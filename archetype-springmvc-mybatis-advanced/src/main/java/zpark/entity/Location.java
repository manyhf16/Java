package zpark.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Location implements Serializable {

	@Override
	public String toString() {
		return "Location [id=" + id + ", name=" + name + "]";
	}

	private int id;
	private String name;

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

}
