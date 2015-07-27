package zpark.struts2.action.day4;

import java.util.ArrayList;
import java.util.List;

import zpark.entity.Person;

public class ListAction {

    private List<Person> list;

    public List<Person> getList() {
        return list;
    }

    public void setList(List<Person> list) {
        this.list = list;
    }

    public String execute() {
        list = new ArrayList<Person>();

        list.add(new Person("王五", 21));
        list.add(new Person("赵六", 22));
        list.add(new Person("钱七", 23));
        list.add(new Person("周八", 24));
        return "success";
    }

}
