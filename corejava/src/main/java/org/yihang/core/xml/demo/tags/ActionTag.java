package org.yihang.core.xml.demo.tags;

import java.util.ArrayList;
import java.util.List;

public class ActionTag {

	private List<ResultTag> resultTagList = new ArrayList<ResultTag>();
	private String name;
	private String clazz;

	public List<ResultTag> getResultTagList() {
		return resultTagList;
	}

	public String getName() {
		return name;
	}

	public String getClazz() {
		return clazz;
	}

	public ActionTag(String name, String clazz) {
		super();
		this.name = name;
		this.clazz = clazz;
	}
	
	public void addResultTag(ResultTag resultTag) {
		this.resultTagList.add(resultTag);
	}

	@Override
	public String toString() {
		return "ActionTag [name=" + name + ", class=" + clazz
				+ ", resultTagList=" + resultTagList + "]";
	}

}
