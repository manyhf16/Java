package zpark.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User4 implements Serializable {

	private int id;

	private String name;

	private UserDetail4 detail;

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

	public UserDetail4 getDetail() {
		return detail;
	}

	public void setDetail(UserDetail4 detail) {
		this.detail = detail;
	}

}
