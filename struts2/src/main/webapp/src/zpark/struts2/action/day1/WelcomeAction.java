package zpark.struts2.action.day1;

import zpark.service.UserService;
import zpark.service.UserServiceImpl;

public class WelcomeAction {

    private String pinyin;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String execute() {
        UserService service = new UserServiceImpl();
        pinyin = service.findPinyin(name);
        return "success";
    }

}
