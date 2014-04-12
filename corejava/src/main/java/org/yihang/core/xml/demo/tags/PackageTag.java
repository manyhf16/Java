package org.yihang.core.xml.demo.tags;

import java.util.ArrayList;
import java.util.List;

public class PackageTag {

	private List<ActionTag> actionTagList = new ArrayList<ActionTag>();
	private String name;
	private String extendz;

	public List<ActionTag> getActionTagList() {
		return actionTagList;
	}

	public String getName() {
		return name;
	}

	public String getExtendz() {
		return extendz;
	}

	public PackageTag(String name, String extendz) {
		super();
		this.name = name;
		this.extendz = extendz;
	}

	public void addActionTag(ActionTag actionTag) {
		this.actionTagList.add(actionTag);
	}

	@Override
	public String toString() {
		return "PackageTag [name=" + name + ", extends=" + extendz
				+ ", actionTagList=" + actionTagList + "]";
	}

}
