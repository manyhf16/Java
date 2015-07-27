package zpark.entity;

import java.util.Date;
import java.util.List;

public class Person {

    private int id;

    private String name;

    private int age;

    private Date birthday;

    private boolean sex; // true 男 false 女

    private List<String> hobby;

    private City city;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + ", birthday=" + birthday + ", sex=" + sex + ", hobby=" + hobby
                + ", city=" + city + "]";
    }

    public Person(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }

    public Person() {
        super();
    }

}
