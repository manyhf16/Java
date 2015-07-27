package zpark.struts2.action.day6;

public class ChainAction1 {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String execute() {
        this.name = "数据库值";
        return "success";
    }

}
