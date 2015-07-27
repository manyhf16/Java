package zpark.actions.day9;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;

import zpark.entity.User;

import com.opensymphony.xwork2.Preparable;

@ParentPackage("day9")
@InterceptorRefs({ @InterceptorRef("a3") })
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

    public void execute() {
        getService().updateUser(user);
        setMessage("更新成功");
        return;
    }

}
