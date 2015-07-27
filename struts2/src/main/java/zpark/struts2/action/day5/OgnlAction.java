package zpark.struts2.action.day5;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;
import zpark.entity.City;
import zpark.entity.Person;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.CompoundRoot;
import com.opensymphony.xwork2.util.ValueStack;

public class OgnlAction {

    private String name;

    private Person person;

    private List<String> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String execute() {
        this.name = "张三";
        this.person = new Person();
        this.person.setName("李四");
        this.person.setAge(18);
        this.person.setCity(new City(1, "北京"));
        this.list = new ArrayList<String>();
        list.add("武大");
        list.add("华工");
        list.add("水院");

        ValueStack vs = ActionContext.getContext().getValueStack();
        Map<String, Object> context = vs.getContext();
        CompoundRoot root = vs.getRoot();
        try {
            System.out.println(Ognl.getValue("name", context, root));
            System.out.println(Ognl.getValue("person.name", context, root));
            System.out.println(Ognl.getValue("person.age", context, root));
            System.out.println(Ognl.getValue("person.city.id", context, root));
            System.out.println(Ognl.getValue("person.city.name", context, root));
        } catch (OgnlException e) {
            e.printStackTrace();
        }
        return "success";
    }

}
