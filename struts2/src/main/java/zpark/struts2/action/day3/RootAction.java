package zpark.struts2.action.day3;

import zpark.entity.City;
import zpark.entity.Person;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

public class RootAction {

    private String name;

    private City city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String execute() {
        name = "李四";
        city = new City(1L, "北京");

        Person u1 = new Person();
        u1.setName("王五");
        u1.setAge(21);

        Person u2 = new Person();
        u2.setName("张三");
        u2.setAge(22);

        // 获得值栈
        ValueStack vs = ActionContext.getContext().getValueStack();

        // 将一些对象压入值栈的Root, 注意先压入的在底下
        vs.push(u1);
        vs.push(u2);
        vs.push("字符串");

        return "success";
    }

}
