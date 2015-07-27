package zpark.struts2.action.common;

import zpark.util.XmlUtil;

public class DisplayStrutsDefaultAction {
    
    private String result;
    
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String execute() {
        result = XmlUtil.getXmlStringFromStrutsDefault();
        return "success";
    }
}
