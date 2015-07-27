package zpark.actions.day10;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;

@ParentPackage("day10")
@InterceptorRefs({ @InterceptorRef("a2") })
public class LoginAction {
    
    public String execute() {
//        return "input";
        return "success";
    }

}
