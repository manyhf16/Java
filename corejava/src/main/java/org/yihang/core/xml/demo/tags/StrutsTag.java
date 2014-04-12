package org.yihang.core.xml.demo.tags;

import java.util.ArrayList;
import java.util.List;

public class StrutsTag {
	
	private List<ConstantTag> constantTagList = new ArrayList<ConstantTag>();
	
	private List<PackageTag> packageTagList = new ArrayList<PackageTag>();

	public List<ConstantTag> getConstantTagList() {
		return constantTagList;
	}
	
	public void addConstantTag(ConstantTag constantTag) {
		this.constantTagList.add(constantTag);
	}

	public List<PackageTag> getPackageTagList() {
		return packageTagList;
	}
	
	public void addPackageTag(PackageTag packageTag) {
		this.packageTagList.add(packageTag);
	}

	public void debugPrint() {
		System.out.println("<struts>");
		for(ConstantTag cTag: constantTagList) {
			System.out.print("\t");
			System.out.printf("<constant name=\"%s\" value=\"%s\"/>", cTag.getName(), cTag.getValue());
			System.out.println();
		}
		for(PackageTag pTag: packageTagList) {
			System.out.print("\t");
			System.out.printf("<package name=\"%s\" extends=\"%s\">", pTag.getName(), pTag.getExtendz());
			System.out.println();
			List<ActionTag> aList = pTag.getActionTagList();
			for (ActionTag aTag : aList) {
				System.out.print("\t\t");
				System.out.printf("<action name=\"%s\" class=\"%s\">", aTag.getName(), aTag.getClazz());
				System.out.println();
				List<ResultTag> rList = aTag.getResultTagList();
				for (ResultTag rTag : rList) {
					System.out.print("\t\t\t");
					System.out.printf("<result name=\"%s\">%s</result>", rTag.getName(), rTag.getLocation());
					System.out.println();
				}
				System.out.println("\t\t</action>");
			}
			System.out.println("\t</package>");
		}
		System.out.println("</struts>");
	}

}
