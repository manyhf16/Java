package zpark.struts2.action.day8;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

@SuppressWarnings("serial")
public class LoginCheckInterceptor implements Interceptor {

    @Override
    public void destroy() {
    }

    @Override
    public void init() {
    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        Object user = ActionContext.getContext().getSession().get("user");
        if(user == null) {
            ActionContext.getContext().getValueStack().setValue("error", "用户尚未登录");
            return "login";
        }
        return invocation.invoke();
    }

}
