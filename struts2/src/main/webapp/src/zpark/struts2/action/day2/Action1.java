package zpark.struts2.action.day2;

import com.opensymphony.xwork2.ActionContext;

public class Action1 {

    public String execute() {
        System.out.println("进入 action1 ... ...");
        String name = "张三"; // 假设从数据库查询

        ActionContext.getContext().getSession().put("name", name);
        return "success";
    }

}
