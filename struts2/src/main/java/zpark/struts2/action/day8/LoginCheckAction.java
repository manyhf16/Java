package zpark.struts2.action.day8;

import org.apache.struts2.dispatcher.SessionMap;

import com.google.gson.annotations.Expose;
import com.opensymphony.xwork2.ActionContext;

import zpark.entity.User;

public class LoginCheckAction extends BaseAction {

    @Expose
    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        User user = getService().findUser(username);
        if (user == null) {
            setError("用户不存在");
            return "login";
        }
        if (!user.getPassword().equals(password)) {
            setError("密码不正确");
            return "login";
        }
        ActionContext.getContext().getSession().put("user", user);
        setMessage("欢迎【" + user.getRealname() + "】登录本系统");
        return "success";
    }

    public String logout() {
        SessionMap<String, Object> session = (SessionMap<String, Object>) ActionContext.getContext().getSession();
        User user = (User) session.get("user");
        session.invalidate();
        setMessage("用户【" + user.getRealname() + "】已退出本系统");
        return "success";
    }

}
