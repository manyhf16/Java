package zpark.struts2.action.day8;

import zpark.entity.User;

import com.opensymphony.xwork2.Preparable;

public class UpdateAction extends BaseAction implements Preparable {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void prepare() throws Exception {
        user = getService().findUser(user.getUsername());
    }

    public String execute() {
        getService().updateUser(user);
        setMessage("更新成功");
        return "success";
    }

}
