package zpark.struts2.action.day2;

import java.util.Date;
import java.util.List;

import zpark.entity.Gender;
import zpark.entity.User;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;

@SuppressWarnings("serial")
public class ParameterAction extends ActionSupport {

    private String name;

    private Integer age;

    private Date birthday;

    private Gender gender;

    private User user;

    private List<String> hobby;

    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Gender getGender() {
        return gender;
    }

    @ConversionErrorFieldValidator
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String execute() {
        return "success";
    }

}
