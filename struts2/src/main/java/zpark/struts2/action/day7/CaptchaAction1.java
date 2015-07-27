package zpark.struts2.action.day7;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import zpark.util.CaptchaUtil;
import zpark.util.ImageInfo;
import zpark.util.ImageUtil;

public class CaptchaAction1 {

    private InputStream inputStream;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String execute() throws IOException {
        HttpSession session = ServletActionContext.getRequest().getSession();
        BufferedImage image = CaptchaUtil.getImage(session.getId());
        ImageInfo info = ImageUtil.getImageInfo(image);
        contentLength = "" + info.getLength();
        inputStream = info.getInputStream();
        return "success";
    }

    public String getContentType() {
        return isDownload() ? "application/octet-stream" : "image/png";
    }

    private String contentLength;

    public String getContentLength() {
        return contentLength;
    }

    private boolean download;

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }

    public String getContentDisposition() throws UnsupportedEncodingException {
        if (isDownload()) {
            // 对于chrome 和 ie 采用下面的方法来编码下载文件名中的汉字
            String name = new String("验证码".getBytes("utf-8"), "iso-8859-1");
            return "attachment;filename=\"" + name + ".png\"";
        } else {
            return "inline";
        }
    }

}
