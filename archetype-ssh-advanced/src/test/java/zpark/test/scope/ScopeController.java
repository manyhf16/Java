package zpark.test.scope;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ScopeController {

    private ScopeObject requestScopeObject;

    private ScopeObject sessionScopeObject;

    public void setRequestScopeObject(ScopeObject requestScopeObject) {
        this.requestScopeObject = requestScopeObject;
    }

    public void setSessionScopeObject(ScopeObject sessionScopeObject) {
        this.sessionScopeObject = sessionScopeObject;
    }

    @RequestMapping("/request")
    @ResponseBody
    public String request(String name, HttpSession session) {
        return requestScopeObject.toString();
    }

    @RequestMapping("/session")
    @ResponseBody
    public String session(String name, HttpSession session) {
        return sessionScopeObject.toString();
    }

}
