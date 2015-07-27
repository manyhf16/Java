package zpark.struts2.action.day7;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import zpark.util.ImageInfo;
import zpark.util.ImageUtil;

public class CropAction {

    private File image;

    private int x1;

    private int y1;

    private int w;

    private int h;

    private String contentLength;

    private InputStream inputStream;

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
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
        // 裁剪图片
        BufferedImage crop = ImageUtil.crop(image.getAbsolutePath(), x1, y1, w, h);
        // 获得图片信息
        ImageInfo info = ImageUtil.getImageInfo(crop);
        contentLength = "" + info.getLength();
        inputStream = info.getInputStream();
        return "success";
    }

}
