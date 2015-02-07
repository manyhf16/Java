package zpark.ext.web;

import java.util.EnumSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.annotation.HandlesTypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zpark.ext.annotations.web.WebServlet;

@HandlesTypes({ Action.class })
public class ZparkServletContainerInitializer implements ServletContainerInitializer {

    private static Logger logger = LoggerFactory.getLogger(ZparkServletContainerInitializer.class);

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext ctx) throws ServletException {
        if (set != null) {
            for (Class<?> c : set) {
                try {
                    WebServlet servletConfig = c.getAnnotation(WebServlet.class);
                    ActionProxy proxy = new ActionProxy(c);
                    Dynamic servlet = ctx.addServlet(servletConfig.name(), proxy);
                    if (servlet != null) {
                        servlet.setLoadOnStartup(servletConfig.loadOnStartup());
                        servlet.addMapping(servletConfig.urlPattern());
                        servlet.setAsyncSupported(servletConfig.asyncSupported());
                        logger.info("servlet [{}] successful registered, url-pattern [{}]", servletConfig.name(),
                                servletConfig.urlPattern());
                    } else {
                        logger.warn("servlet [{}] already registered", servletConfig.name());
                    }
                    if (proxy.needCacheFilter()) {
                        ActionCachingFilter filter = new ActionCachingFilter("zpark:"+servletConfig.name(), 10, 60, proxy);
                        javax.servlet.FilterRegistration.Dynamic addFilter = ctx.addFilter("ActionCachingFilter_"
                                + servletConfig.name(), filter);
                        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST,
                                DispatcherType.INCLUDE);
                        addFilter.addMappingForUrlPatterns(dispatcherTypes, false, servletConfig.urlPattern());
                        logger.info("filter [{}] successful registered, url-pattern [{}]", "ActionCachingFilter_"
                                + servletConfig.name(), servletConfig.urlPattern());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // throw new
                    // ZparkException("error when create action", e);
                }
            }
        }
    }
}
