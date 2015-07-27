package zpark.struts2.action.day4;

import zpark.entity.Person;

public class NormalAction {
    
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    
    public String execute() {
        return "success";
    }

}
