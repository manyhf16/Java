package zpark.actions.day9;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.dispatcher.SessionMap;

import zpark.entity.User;

import com.opensymphony.xwork2.ActionContext;

@ParentPackage("day9")
public class LogoutAction extends BaseAction {

    public void execute() {
        SessionMap<String, Object> session = (SessionMap<String, Object>) ActionContext.getContext().getSession();
        User user = (User) session.get("user");
        session.invalidate();
        setMessage("用户【" + user.getRealname() + "】已退出本系统");
        return;
    }

}
