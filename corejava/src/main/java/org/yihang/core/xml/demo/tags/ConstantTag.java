package org.yihang.core.xml.demo.tags;

public class ConstantTag {

	private String name;
	private String value;

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public ConstantTag(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	@Override
	public String toString() {
		return "ConstantTag [name=" + name + ", value=" + value + "]";
	}

}
