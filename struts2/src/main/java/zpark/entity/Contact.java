package zpark.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Contact implements Serializable {

    private int id;

    private String name;

    private String qq;

    private String phone;

    private String email;

    private Long cityId;

    private String username;

    private String photo; // 图片名字

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

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

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Contact [username=" + username + ", id=" + id + ", name=" + name + ", cityId=" + cityId + ", email="
                + email + ", phone=" + phone + ", qq=" + qq + "]";
    }

}
