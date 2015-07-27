package zpark.struts2.action.day8;

import zpark.entity.User;

public class FindAction extends BaseAction {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String execute() {
        user = getService().findUser(user.getUsername());
        return "success";
    }

}
