package zpark.util;

import java.io.InputStream;

public class ImageInfo {

    // 图片输入流
    private InputStream inputStream;

    // 图片大小(占用字节长度)
    private long length;

    public InputStream getInputStream() {
        return inputStream;
    }

    public long getLength() {
        return length;
    }

    public ImageInfo(InputStream inputStream, long length) {
        super();
        this.inputStream = inputStream;
        this.length = length;
    }

}
