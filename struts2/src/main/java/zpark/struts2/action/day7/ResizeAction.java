package zpark.struts2.action.day7;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import zpark.util.ImageInfo;
import zpark.util.ImageUtil;

public class ResizeAction {

    private File image;

    private String contentLength;

    private InputStream inputStream;

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getContentType() {
        return "image/png";
    }

    public String getContentLength() {
        return contentLength;
    }

    public String execute() throws FileNotFoundException {
        ImageUtil.getImageType(image.getAbsolutePath());
        // 改变大小
        BufferedImage crop = ImageUtil.resize(image.getAbsolutePath(), 100, 100);
        // 获得图片信息
        ImageInfo info = ImageUtil.getImageInfo(crop);
        contentLength = "" + info.getLength();
        inputStream = info.getInputStream();
        return "success";
    }

}
