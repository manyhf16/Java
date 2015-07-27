package zpark.struts2.action.day6;

import java.util.Date;

import zpark.entity.Person;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.DateRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@SuppressWarnings("serial")
@Validations(requiredStrings={
        @RequiredStringValidator(message = "您必须提供Person的姓名" , trim = true, fieldName="person.name")
})
public class ValidationAction extends ActionSupport {
    private Date birthday;
    
    private String email;
    
    private String pass;
    
    private String passConfirm;
    
    private String phone;
    
    private Person person;
    
    private int age;

    public Date getMax() {
        return new Date();
    }

    public Date getBirthday() {
        return birthday;
    }

    // maxExpression是一个Ognl表达式，限制生日的最大值是当前时间，也可以写成 maxExpression= "${max}"（从值栈中获取max属性的值）
    @DateRangeFieldValidator(
            message = "生日必须在${min.year+1900}-${min.month+1}-${min.date}和${max.year+1900}-${max.month+1}-${max.date}范围之间", 
            dateFormat = "yyyy-MM-dd", 
            min = "1960-1-1", 
            maxExpression = "${new java.util.Date()}"
    )
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    } 
    
    public String getEmail() {
        return email;
    }

    @EmailValidator(message = "您输入的email地址格式错误")
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPass() {
        return pass;
    }
    
    @RequiredStringValidator(message = "您必须提供密码" , trim = true)
    @StringLengthFieldValidator(message = "密码长度必须至少6位", minLength = "6", trim = true)
    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPassConfirm() {
        return passConfirm;
    }

    @FieldExpressionValidator(message = "您输入的密码必须一致", expression="pass == passConfirm")
    public void setPassConfirm(String passConfirm) {
        this.passConfirm = passConfirm;
    }

    public String getPhone() {
        return phone;
    }

    @RegexFieldValidator(message = "电话号码格式必须类似于(086) 1234-56789", regex = "^\\(\\d{3}\\) \\d{4}-\\d{5}$")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Person getPerson() {
        return person;
    }
    
    public void setPerson(Person person) {
        this.person = person;
    }

    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }

    public String execute() {
        return "success";
    }

}
