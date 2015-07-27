package zpark.struts2.action.day3;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

public class ContextAction {

    public String execute() {
        // 1. 直接向Context 存键值
        ActionContext.getContext().put("name", "a");

        // 2. 向原始request对象存键值
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("name", "b");

        // 3. 向Session作用域存键值
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("name", "c");

        // 4. 向Application作用域存键值
        Map<String, Object> application = ActionContext.getContext().getApplication();
        application.put("name", "d");

        return "success";
    }

}
