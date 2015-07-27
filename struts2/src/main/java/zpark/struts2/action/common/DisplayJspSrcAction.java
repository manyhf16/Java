package zpark.struts2.action.common;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

public class DisplayJspSrcAction {

    private String type = "jsp";

    private String src;

    private String result;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String execute() throws IOException {
        String path = ServletActionContext.getServletContext().getRealPath("/");
        result = FileUtils.readFileToString(new File(path + src), "utf-8");
        return "success";
    }

}