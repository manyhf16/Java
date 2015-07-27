package zpark.actions.day11;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zpark.entity.Contact;
import zpark.ext.jdbc.JdbcUtil;
import zpark.ext.struts.tag.Column;
import zpark.ext.struts.tag.PagerAction;
import zpark.service.UserService;
import zpark.service.UserServiceImpl;

public class FindAction extends PagerAction {

    private String name;
    private String qq;
    private Integer cityId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    private UserService userService = JdbcUtil.createProxy(new UserServiceImpl(), UserService.class);

    private Map<Long, String> map;

    public Map<Long, String> getMap() {
        return map;
    }

    public void setMap(Map<Long, String> map) {
        this.map = map;
    }

//    @SuppressWarnings("serial")
//    private List<Column> COLUMNS = new ArrayList<Column>() {
//        {
//            add(new Column("编号", "id"));
//            add(new Column("姓名", "name"));
//            add(new Column("QQ", "qq"));
//            add(new Column("手机", "phone"));
//            add(new Column("邮件", "email"));
//            add(new Column("城市", "cityId") {
//                public Object translate(Object cityId) {
//                    return map.get(cityId);
//                };
//            });
//            add(new Column("更新", "id") {
//                public Object translate(Object id) {
//                    return "<a href='update.action?id="+id+"'>更新</a>";
//                };
//            });
//        }
//    };
//
//    @Override
//    public List<Column> getColumns() {
//        return COLUMNS;
//    }

    public String execute() {
//        map = userService.findCityMap();
//        User user = (User) ActionContext.getContext().getSession().get("user");
//        String username = "zhangsan";
//        JdbcUtil.isDebug = true;
//        List<Contact> list = userService.findContacts(name, qq, cityId, username, getPageNo(), getPageSize());
//        int count = userService.findCount(name, qq, cityId, username);
//        createPager(list, count);
        System.out.println(cityId);
        return "success";
    }

}
