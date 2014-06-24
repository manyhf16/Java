package zpark.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User3 implements Serializable {
	private int id;

	private String name;

	private UserDetail3 detail;

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

	public UserDetail3 getDetail() {
		return detail;
	}

	public void setDetail(UserDetail3 detail) {
		this.detail = detail;
	}

}
