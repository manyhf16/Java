package zpark.actions.day9;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;

import zpark.entity.User;

import com.google.gson.annotations.Expose;
import com.opensymphony.xwork2.ActionContext;

@ParentPackage("day9")
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

    public void execute() {
        User user = getService().findUser(username);
        if (user == null) {
            setError("用户不存在");
            return;
        }
        if (!user.getPassword().equals(password)) {
            setError("密码不正确");
            return;
        }
        ActionContext.getContext().getSession().put("user", user);
        setMessage("欢迎【" + user.getRealname() + "】登录本系统");
        return;
    }

}
