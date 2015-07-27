package zpark.ext.struts;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;

/**
 * 该拦截器的目的是模拟Flash作用域，用来在重定向时传递数据 在响应结束前将加了 @Flash 注解的Action属性存入#session
 * 在进入重定向后的Action时，将#session中存储的值转存到#flash，并清除session中的数据
 * #flash其实是一个map, 存储于context之中，作用范围只限于一次请求
 * 
 * @author yihang
 *
 */
@SuppressWarnings("serial")
public class FlashScopeInterceptor implements Interceptor {

    private static final String PREFIX = "@flash_";

    @Override
    public void destroy() {
    }

    @Override
    public void init() {
    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        final Object action = invocation.getAction();
        final Map<String, Object> session = invocation.getInvocationContext().getSession();
        ArrayList<String> keyToRemove = new ArrayList<String>();
        Map<String, Object> flashMap = new HashMap<String, Object>();
        invocation.getInvocationContext().put("flash", flashMap);
        // 将session中的值转存入#flash中
        for (String key : session.keySet()) {
            if (key.startsWith(PREFIX)) {
                Object value = session.get(key);
                flashMap.put(key.substring(PREFIX.length()), value);
                keyToRemove.add(key);
            }
        }
        // 移除session中这些临时值
        for (String key : keyToRemove) {
            session.remove(key);
        }
        HttpServletRequest request = ServletActionContext.getRequest();
        String ajaxHeader = request.getHeader("X-Requested-With");
        // 如果是ajax请求不需要考虑flash作用域
        if (ajaxHeader == null || !ajaxHeader.equalsIgnoreCase("XMLHttpRequest")) {
            invocation.addPreResultListener(new PreResultListener() {
                @Override
                public void beforeResult(ActionInvocation invocation, String resultCode) {
                    try {
                        BeanInfo beanInfo = Introspector.getBeanInfo(action.getClass());
                        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
                        for (int i = 0; i < pds.length; i++) {
                            Method get = pds[i].getReadMethod();
                            if (get != null) {
                                Flash flash = get.getAnnotation(Flash.class);
                                // 如果属性(get 方法)上有@Flash注解，那么将该属性值存入session
                                if (flash != null) {
                                    session.put(PREFIX + pds[i].getName(), pds[i].getReadMethod().invoke(action));
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return invocation.invoke();
    }

}
