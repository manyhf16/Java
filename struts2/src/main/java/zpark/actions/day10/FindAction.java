package zpark.actions.day10;

import org.apache.struts2.convention.annotation.ParentPackage;

import zpark.entity.User;

import com.google.gson.annotations.Expose;

@ParentPackage("day10")
public class FindAction extends BaseAction {

    @Expose
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
