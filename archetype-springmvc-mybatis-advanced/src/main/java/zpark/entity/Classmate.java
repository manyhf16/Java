package zpark.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Classmate implements Serializable{

	private int id;
	private String username;
	private String password;
	private String name;
	private String gender;
	private Date birthday;
	private String constellation;
	private String mobile;
	private String email;
	private String organization;
	private String remark;
	private boolean lost;
	private Location location;
	private List<String> tags;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isLost() {
		return lost;
	}

	public void setLost(boolean lost) {
		this.lost = lost;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "Classmate [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", gender=" + gender
				+ ", birthday=" + birthday + ", constellation=" + constellation + ", mobile=" + mobile + ", email=" + email
				+ ", organization=" + organization + ", remark=" + remark + ", lost=" + lost + ", location=" + location + ", tags=" + tags
				+ "]";
	}

}
