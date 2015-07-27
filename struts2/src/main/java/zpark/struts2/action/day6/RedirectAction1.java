package zpark.struts2.action.day6;

import com.opensymphony.xwork2.ActionContext;

public class RedirectAction1 {

    private String n1;

    public String getN1() {
        return n1;
    }

    public void setN1(String n1) {
        this.n1 = n1;
    }

    public String execute() {
        System.out.println("进入 action1 ... ...");
        String name = "zhangsan"; // 假设从数据库查询

        // 现在目标是将该值，从此Action传递到另一个Action，中间使用重定向跳转

        // 方法1：利用session
        ActionContext.getContext().getSession().put("name", name);

        // 方法2：利用值栈
        this.n1 = name;
        return "success";
    }

}
