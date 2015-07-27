package zpark.ext.struts;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;

/**
 * 该拦截器的目的是检查是否为xhr请求，如果是那么会将action转换为json作为响应返回，否则执行正常流程
 * 
 * @author yihang
 *
 */
@SuppressWarnings("serial")
public class JsonResultInterceptor implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(JsonResultInterceptor.class);

    private Gson gson;

    @Override
    public void destroy() {
    }

    @Override
    public void init() {
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        final HttpServletResponse response = ServletActionContext.getResponse();
        String ajaxHeader = request.getHeader("X-Requested-With");
        if (ajaxHeader != null && ajaxHeader.equalsIgnoreCase("XMLHttpRequest")) {
            invocation.addPreResultListener(new PreResultListener() {
                @Override
                public void beforeResult(ActionInvocation invocation, String resultCode) {
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("application/json");
                    try {
                        String json = gson.toJson(invocation.getAction());
                        response.getWriter().print(json);
                    } catch (Exception e) {
                        logger.error("json mapper error", e);
                        try {
                            response.getWriter().print("{\"error\":\"" + e.getMessage() + "\"}");
                        } catch (IOException ex) {
                        }
                    } finally {
                        // 让后续操作不必执行视图(result)
                        invocation.getProxy().setExecuteResult(false);
                    }
                }
            });
        }
        return invocation.invoke();
    }

}
