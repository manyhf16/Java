package zpark.struts2.action.day6;

public class ChainAction2 {
    
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String execute() {
        return "success";
    }

}
