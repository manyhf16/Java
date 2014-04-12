package org.yihang.core.xml.demo.tags;

public class ResultTag {

	private String name;
	private String location;

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public ResultTag(String name, String location) {
		super();
		this.name = name;
		this.location = location;
	}

	@Override
	public String toString() {
		return "ResultTag [name=" + name + ", location=" + location + "]";
	}

}
