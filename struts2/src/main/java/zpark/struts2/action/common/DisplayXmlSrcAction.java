package zpark.struts2.action.common;

import zpark.util.XmlUtil;

public class DisplayXmlSrcAction {
    
    private String type = "xml";

    private String packageName;
    
    private String result;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String execute() {
        result = XmlUtil.getXmlStringFromStrutsPackageName(packageName);
        return "success";
    }
}
