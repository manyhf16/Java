package zpark.action.servlet;

import java.util.List;

import zpark.ext.annotations.web.RequestMethod;
import zpark.ext.annotations.web.RequestParam;
import zpark.ext.annotations.web.Rule.CustomRule;
import zpark.ext.annotations.web.WebServlet;
import zpark.ext.web.Action;
import zpark.ext.web.view.View;

@WebServlet(name = "user", urlPattern = "/user")
public class UserAction implements Action {

    public static class MyCheck implements CustomRule {

        @Override
        public boolean check(String value, CustomRule callback, String options) {
            // TODO Auto-generated method stub
            return false;
        }

    }

//    @RequestMethod(cacheable = true)
//    public View execute(
//            @RequestParam(name = "name", rules = { @Rule(type = RuleType.CUSTOM, custom = MyCheck.class, message = "姓名不能为空") }) String name,
//            @RequestParam(name = "age", rules = { @Rule(type = RuleType.IS_INTEGER, message = "年龄必须为整型") }) Integer age,
//            HttpSession session) {
//        return null;
//    }

//    @RequestMethod(cacheable = true)
//    public View b(@RequestParam(name = "name") Date date, @RequestParam(name = "age") Integer age, HttpSession session) {
//        return null;
//    }
    
    @RequestMethod(cacheable = true)
    public View c(@RequestParam(name = "name") List<String> list) {
        return null;
    }

}
