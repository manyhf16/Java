package zpark.struts2.action.day4;

import zpark.entity.Person;

import com.opensymphony.xwork2.ModelDriven;

public class ModelDrivenAction implements ModelDriven<Person>{

    @Override
    public Person getModel() {
        return new Person();
    }
    
    public String execute() {
        return "success";
    }

}
