package zpark.actions.day10;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.dispatcher.SessionMap;

import zpark.entity.User;

import com.opensymphony.xwork2.ActionContext;

@ParentPackage("day10")
public class LogoutAction extends BaseAction {

    @Action(results={@Result(name="success", type="redirect", location="login.action")})
    public String execute() {
        SessionMap<String, Object> session = (SessionMap<String, Object>) ActionContext.getContext().getSession();
        User user = (User) session.get("user");
        session.invalidate();
        setMessage("用户【" + user.getRealname() + "】已退出本系统");
        return "success";
    }

}
