package zpark.actions.day10;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import zpark.entity.User;

import com.google.gson.annotations.Expose;
import com.opensymphony.xwork2.ActionContext;

@ParentPackage("day10")
@InterceptorRefs({ @InterceptorRef("a2") })
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

    @Action(results={@Result(name="login", type="redirect", location="login.action"), @Result(name="success", type="redirect", location="login.action")})
    public String execute() {
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

}
