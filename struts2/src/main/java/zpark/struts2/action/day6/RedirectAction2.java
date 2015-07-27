package zpark.struts2.action.day6;

import com.opensymphony.xwork2.ActionContext;

public class RedirectAction2 {

    private String n2;

    public String getN2() {
        return n2;
    }

    public void setN2(String n2) {
        this.n2 = n2;
    }

    public String execute() {
        System.out.println("进入 action2 ... ...");
        // 用方法1接收
        System.out.println(ActionContext.getContext().getSession().get("name"));
        
        // 用方法2接收
        System.out.println(n2);        
        return "success";
    }

}
