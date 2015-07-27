package zpark.struts2.action.day1;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

public class ScopeAction {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String execute() {
        name = "张三";
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("name", "李四");

        Map<String, Object> app = ActionContext.getContext().getApplication();
        app.put("name", "王五");

        return "success";
    }

}
