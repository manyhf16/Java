package zpark.struts2.action.day7;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.struts2.ServletActionContext;

import zpark.util.ImageInfo;
import zpark.util.ImageUtil;

public class WatermarkAction {

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

    public String getContentLength() {
        return contentLength;
    }

    public String getContentType() {
        return "image/png";
    }

    private String getWatermarkPath() {
        // 根据实际情况可以改写水印图片的存储路径
        return ServletActionContext.getServletContext().getRealPath("/image/1.png");
    }

    public String execute() throws FileNotFoundException {
        ImageUtil.getImageType(image.getAbsolutePath());
        // 添加水印
        BufferedImage crop = ImageUtil.watermark(getWatermarkPath(), "SouthEast", image.getAbsolutePath());
        // 获得图片信息
        ImageInfo info = ImageUtil.getImageInfo(crop);
        contentLength = "" + info.getLength();
        inputStream = info.getInputStream();
        return "success";
    }

}
