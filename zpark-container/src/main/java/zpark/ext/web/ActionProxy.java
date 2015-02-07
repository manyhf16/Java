package zpark.ext.web;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zpark.ext.annotations.web.PathParam;
import zpark.ext.annotations.web.RequestMethod;
import zpark.ext.annotations.web.RequestParam;
import zpark.ext.annotations.web.Rule;
import zpark.ext.annotations.web.Rule.CustomRule;
import zpark.ext.annotations.web.Rule.RuleType;
import zpark.ext.annotations.web.WebServlet;
import zpark.ext.exception.ZparkException;
import zpark.ext.web.view.View;
import zpark.ext.web.view.ViewResolvers;

@SuppressWarnings("serial")
public class ActionProxy extends HttpServlet implements WebConstrants {

    private static final Logger logger = LoggerFactory.getLogger(ActionProxy.class);

    private Action action;

    private Class<?> actionClass;

    private WebServlet servletConfig;

    private Map<String, Map<String, Object>> uriMappings = new HashMap<>();

    private boolean needCacheFilter = false;

    public boolean needCacheFilter() {
        return needCacheFilter;
    }

    /**
     * 处理Action方法的路径
     * <p>
     * 因为要符合Restful风格，路径中会有参数存在
     * <p>
     * 例如: {@link WebServlet} 的urlPattern为/hello/*, {@link RequestMethod}
     * 的path为f/{id}/page/{num}
     * <p>
     * 则最后的路径为:
     * <p>
     * <li>/hello/f/{id}/page/{name}
     * <p>
     * 为了利用正则对用户实际输入的路径进行匹配, 需要将此路径转换为正则形式:
     * <p>
     * <li>/hello/f/[^/=?]+?/page/[^/=?]+?
     * 
     * @param path
     *            {@link RequestMethod}的path值
     * @return
     */
    private String getPathPatternStr(String path) {
        String key = getKey(path);
        return "^" + key.replaceAll("\\{.+?\\}", "[^/=?]+?") + "$";
    }

    private Pattern getPathPattern(String patternStr) {
        return Pattern.compile(patternStr);
    }

    /**
     * 获取Action方法的路径
     * <p>
     * 例如: {@link WebServlet} 的urlPattern为/hello/*, {@link RequestMethod}的path为c
     * <p>
     * 则最后的路径为/hello/c
     * 
     * @param path
     *            {@link RequestMethod}的path值
     * @return
     */
    private String getKey(String path) {
        String prefix = servletConfig.urlPattern().replaceAll("/\\*", "");
        String key = path.equals("") ? prefix : (prefix + "/" + path);
        return key;
    }

    /**
     * 分离出路径中的参数名和参数值
     * 
     * @param path
     *            {@link RequestMethod}的path值
     * @param uri
     *            实际请求路径
     * @return Map 的key为参数名，value为实际值
     */
    private Map<String, String> handlePathParam(String path, String uri) {
        Map<String, String> pathParams = new HashMap<>();
        String key = getKey(path);
        Matcher m1 = Pattern.compile(("^" + key + "$").replaceAll("\\{.+?\\}", "\\\\{([^/=?]+?)\\\\}")).matcher(key);
        Matcher m2 = Pattern.compile(("^" + key + "$").replaceAll("\\{.+?\\}", "([^/=?]+?)")).matcher(uri);
        while (m1.find() && m2.find()) {
            for (int i = 1; i <= m1.groupCount(); i++) {
                pathParams.put(m1.group(i), m2.group(i));
            }
        }
        return pathParams;
    }

    private Method findMethod(String uri, String... requestMethods) {
        for (String patternStr : uriMappings.keySet()) {
            Map<String, Object> map = uriMappings.get(patternStr);
            if (((Pattern) map.get("pattern")).matcher(uri).find()) {
                Method m = (Method) map.get("method");
                if (requestMethods != null && requestMethods.length > 0) {
                    for (String requestMethod : requestMethods) {
                        if (m.getAnnotation(RequestMethod.class).method().toString().equals(requestMethod)) {
                            return m;
                        }
                    }
                    return null;
                } else {
                    return m;
                }
            }
        }
        return null;
    }

    public boolean uriCacheable(String uri) {
        Method m = findMethod(uri);
        if (m == null) {
            return false;
        }
        return m.getAnnotation(RequestMethod.class).cacheable();
    }

    public boolean uriEvictCache(String uri) {
        Method m = findMethod(uri);
        if (m == null) {
            return false;
        }
        return m.getAnnotation(RequestMethod.class).evictcache();
    }

    public ActionProxy(Class<?> actionClass) throws InstantiationException, IllegalAccessException {
        this.actionClass = actionClass;
        this.action = (Action) this.actionClass.newInstance();
        servletConfig = this.actionClass.getAnnotation(WebServlet.class);
        Method[] methods = actionClass.getMethods();
        for (Method m : methods) {
            RequestMethod methodConfig = m.getAnnotation(RequestMethod.class);
            if (methodConfig != null) {
                if (methodConfig.cacheable()) {
                    needCacheFilter = true;
                }
                String patternStr = getPathPatternStr(methodConfig.path());
                Pattern pattern = getPathPattern(patternStr);
                if (uriMappings.containsKey(patternStr)) {
                    Object method = uriMappings.get(patternStr).get("method");
                    logger.warn("url-pattern: [{}] is already registered for class {}, method: {} is ignored.",
                            getKey(methodConfig.path()), actionClass, method);
                }
                Map<String, Object> map = new HashMap<>();
                map.put("pattern", pattern);
                map.put("method", m);
                uriMappings.put(patternStr, map);
            }
        }
        if (uriMappings.size() == 0) {
            throw new InstantiationException("action " + actionClass + " must contains one request method");
        }
    }

    protected void doInternal(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI().replaceAll(req.getContextPath(), "");
        Method m = findMethod(uri, req.getMethod());
        if (m == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "no such uri [" + uri + "] with method [" + req.getMethod() + "] for action: " + action.getClass());
            return;
        }
        Object[] actualValues = null;
        try {
            actualValues = handleRequestParam(m, uri, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }
        logger.debug("all parameters: {}", Arrays.toString(actualValues));
        View view;
        try {
            view = (View) m.invoke(action, actualValues);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            return;
        }
        if(view != null) {
            try {
                ViewResolvers.findViewResolver(view).resolve(view, resp);
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                return;
            }
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if (method.equals(METHOD_GET)) {
            doInternal(req, resp);
        } else if (method.equals(METHOD_HEAD)) {
            doInternal(req, resp);
        } else if (method.equals(METHOD_POST)) {
            doInternal(req, resp);
        } else if (method.equals(METHOD_PUT)) {
            doInternal(req, resp);
        } else if (method.equals(METHOD_DELETE)) {
            doInternal(req, resp);
        } else if (method.equals(METHOD_OPTIONS)) {
            doInternal(req, resp);
        } else if (method.equals(METHOD_TRACE)) {
            doInternal(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "request method[" + method + "] not implemented");
        }
    }

    private Object[] handleRequestParam(Method m, String uri, HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Map<String, String> pathParams = handlePathParam(m.getAnnotation(RequestMethod.class).path(), uri);
        logger.debug("path parameters: {}", pathParams);
        Annotation[][] annotations = m.getParameterAnnotations();
        Class<?>[] types = m.getParameterTypes();
        Object[] actualValues = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            Annotation[] a = annotations[i];
            if (a.length > 0) { // 该参数有注解
                boolean found = false;
                for (int j = 0; j < a.length; j++) {
                    if (a[j] instanceof PathParam) {
                        PathParam paramInfo = (PathParam) a[j];
                        String originalValue = pathParams.get(paramInfo.name());
                        if (originalValue == null) {
                            throw new ZparkException("request path [" + uri + "] is incomplete");
                        }
                        if (types[i] != String.class) {
                            // 处理请求参数类型转换
                            RequestParamConverter<?> converter = RequestParamConverter.converters.get(types[i]);
                            if (converter == null) {
                                throw new ZparkException("no converter for type: " + types[i]);
                            }
                            Object actualValue = converter.convert(new String[] { originalValue }, null);
                            actualValues[i] = actualValue;
                        } else {
                            actualValues[i] = originalValue;
                        }
                        found = true;
                        break;
                    } else if (a[j] instanceof RequestParam) { // 是RequestParam注解
                        RequestParam paramInfo = (RequestParam) a[j];
                        String[] originalValue = req.getParameterValues(paramInfo.name());
                        // 处理请求参数默认值
                        originalValue = (originalValue != null) ? originalValue : paramInfo.defaultValue();
                        // 处理请求参数验证
                        // applyRule(originalValue, paramInfo.rules(), req);
                        if (originalValue.length == 0) {
                            actualValues[i] = null;
                        } else if (types[i] == List.class) {
                            RequestParamConverter<?> converter = RequestParamConverter.converters.get(types[i]);
                            Type type = m.getGenericParameterTypes()[i];
                            Object actualValue;
                            if (type instanceof ParameterizedType) {
                                ParameterizedType t = (ParameterizedType) type;
                                Class<?> c = (Class<?>) t.getActualTypeArguments()[0];
                                actualValue = converter.convert(originalValue, c);
                            } else {
                                actualValue = converter.convert(originalValue, null);
                            }
                            actualValues[i] = actualValue;
                        } else if (types[i] != String.class) {
                            // 处理请求参数类型转换
                            RequestParamConverter<?> converter = RequestParamConverter.converters.get(types[i]);
                            if (converter == null) {
                                throw new ZparkException("no converter for type: " + types[i]);
                            }
                            Object actualValue = converter.convert(originalValue, null);
                            actualValues[i] = actualValue;
                        } else {
                            actualValues[i] = originalValue[0];
                        }
                        found = true;
                        break;
                    }
                }
                // 处理没有RequestParam和PathParam注解的情况
                if (!found) {
                    handleNonRequestParamType(actualValues, i, types[i], req, resp);
                }
            } else {// 处理没有注解情况
                handleNonRequestParamType(actualValues, i, types[i], req, resp);
            }
        }
        return actualValues;
    }

    private void applyRule(String originalValue, Rule[] rules, HttpServletRequest req) {
        for (Rule rule : rules) {
            RuleType type = rule.type();
            Class<?> ruleClass = rule.custom() == CustomRule.class ? null : rule.custom();
            CustomRule customRule = null;
            if (ruleClass != null) {
                System.out.println(".............................." + ruleClass);
                try {
                    customRule = (CustomRule) ruleClass.newInstance();
                } catch (Exception e) {
                    throw new ZparkException("your rule class: [" + ruleClass + "] maybe not implements CustomRule", e);
                }
            }
            if (!type.check(originalValue, customRule, rule.options())) {
                System.out.println(rule.message());
                throw new ZparkException("validate request parameter error");
            }
        }
    }

    private void handleNonRequestParamType(Object[] actualValues, int i, Class<?> type, HttpServletRequest req,
            HttpServletResponse resp) {
        if (type.isAssignableFrom(HttpServletRequest.class)) {
            actualValues[i] = req;
        } else if (type.isAssignableFrom(HttpServletResponse.class)) {
            actualValues[i] = resp;
        } else if (type.isAssignableFrom(HttpSession.class)) {
            actualValues[i] = req.getSession();
        } else if (type.isAssignableFrom(ServletContext.class)) {
            actualValues[i] = req.getServletContext();
        } else {
            actualValues[i] = null;
        }
    }
}
