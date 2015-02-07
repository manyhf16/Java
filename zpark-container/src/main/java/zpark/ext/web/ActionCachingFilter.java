package zpark.ext.web;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.constructs.blocking.BlockingCache;
import net.sf.ehcache.constructs.blocking.LockTimeoutException;
import net.sf.ehcache.constructs.web.AlreadyCommittedException;
import net.sf.ehcache.constructs.web.AlreadyGzippedException;
import net.sf.ehcache.constructs.web.PageInfo;
import net.sf.ehcache.constructs.web.filter.CachingFilter;
import net.sf.ehcache.constructs.web.filter.FilterNonReentrantException;

public class ActionCachingFilter extends CachingFilter implements WebConstrants {

    private static final Logger logger = LoggerFactory.getLogger(ActionCachingFilter.class);

    private static CacheManager cacheManager = CacheManager.getInstance();

    private ActionProxy actionProxy;

    public ActionCachingFilter(String cacheName, int maxElementsInMemory, int timeToIdleSeconds, ActionProxy actionProxy) {
        this.cacheName = cacheName;
        Cache cache = new Cache(new CacheConfiguration().name(cacheName).maxElementsInMemory(maxElementsInMemory)
                .overflowToDisk(false).timeToIdleSeconds(timeToIdleSeconds));
        blockingCache = new BlockingCache(cache);
        blockingCache.setTimeoutMillis(10000);
        cacheManager.addCacheIfAbsent(blockingCache);
        this.actionProxy = actionProxy;
    }

    @Override
    public void doInit(FilterConfig filterConfig) throws CacheException {

    }

    @Override
    protected void doDestroy() {
        blockingCache.removeAll();
    }

    @Override
    protected CacheManager getCacheManager() {
        return cacheManager;
    }

    @Override
    protected String calculateKey(HttpServletRequest req) {
        return calculateRequestKey(req);
    }

    public static String calculateRequestKey(HttpServletRequest req) {
        StringBuffer stringBuffer = new StringBuffer();
        // req.getSession().getId()
        stringBuffer.append(req.getMethod()).append(req.getRequestURI());
        if (req.getQueryString() != null) {
            stringBuffer.append(req.getQueryString());
        }
        String key = stringBuffer.toString();
        return key;
    }

    protected long getLastModified(HttpServletRequest req) {
        String key = ActionCachingFilter.calculateRequestKey(req);
        Element element = blockingCache.get(key);
        if (element != null) {
            logger.debug("uri {} is hit in cache: {}", key, cacheName);
            return ((element.getLastUpdateTime() != 0) ? element.getLastUpdateTime() : element.getCreationTime()) / 1000 * 1000;
        } else {
            logger.debug("uri {} is miss in cache: {}", key, cacheName);
            blockingCache.put(new Element(key, null));
            return -1;
        }
    }

    private void maybeSetLastModified(HttpServletResponse resp, long lastModified) {
        if (resp.containsHeader(HEADER_LASTMOD)) {
            return;
        }
        if (lastModified >= 0) {
            resp.setDateHeader(HEADER_LASTMOD, lastModified);
        }
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws AlreadyGzippedException, AlreadyCommittedException, FilterNonReentrantException,
            LockTimeoutException, Exception {
        String uri = req.getRequestURI().replaceAll(req.getContextPath(), "");
        String requestMethod = req.getMethod();
        if (actionProxy.uriEvictCache(uri)) {
            blockingCache.removeAll();
            logger.debug("cache: {} is clear", cacheName);
        }
        if (actionProxy.uriCacheable(uri)) {
            if (requestMethod.equals(METHOD_GET)) {
                long lastModified = getLastModified(req);
                if (lastModified == -1) {
                    if (resp.isCommitted()) {
                        throw new AlreadyCommittedException("Response already committed before doing buildPage.");
                    }
                    logRequestHeaders(req);
                    PageInfo pageInfo = buildPageInfo(req, resp, chain);
                    if (pageInfo.isOk()) {
                        if (resp.isCommitted()) {
                            throw new AlreadyCommittedException("Response already committed after doing buildPage"
                                    + " but before writing response from PageInfo.");
                        }
                        maybeSetLastModified(resp, getLastModified(req));
                        writeResponse(req, resp, pageInfo);
                    }
                } else {
                    long ifModifiedSince = req.getDateHeader(HEADER_IFMODSINCE);
                    if (ifModifiedSince < lastModified) {
                        // If the servlet mod time is later, call doGet()
                        // Round down to the nearest second for a proper compare
                        // A ifModifiedSince of -1 will always be less
                        maybeSetLastModified(resp, lastModified);
                        super.doFilter(req, resp, chain);
                    } else {
                        resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                        return;
                    }
                }
            } else if (requestMethod.equals(METHOD_HEAD)) {
                long lastModified = getLastModified(req);
                maybeSetLastModified(resp, lastModified);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "only GET and HEAD request method support cache");
                return;
            }
        } else {
            chain.doFilter(req, resp);
        }

    }

}
