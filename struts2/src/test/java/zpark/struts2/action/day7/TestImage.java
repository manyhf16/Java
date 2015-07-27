package zpark.struts2.action.day7;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.im4java.process.ProcessStarter;
import org.junit.Assert;
import org.junit.Test;

import zpark.util.ImageUtil;

public class TestImage {

    private static String userDir = System.getProperty("user.dir");
    private static String imageDir = userDir + "\\src\\main\\webapp\\image\\";
    private static String cmdPath = "C:\\Program Files (x86)\\GraphicsMagick-1.3.19-Q8";
    static {
        ProcessStarter.setGlobalSearchPath(cmdPath);
    }

    @Test
    public void test01() {
        ImageUtil.crop(imageDir + "pool.jpg", "d:\\p1.png", 0, 0, 200, 200);
    }

    @Test
    public void test02() throws FileNotFoundException, IOException {
        BufferedImage image = ImageUtil.resize(imageDir + "pool.jpg", 100, 100);
        ImageIO.write(image, "png", new FileOutputStream("d:\\p2.png"));
    }

    @Test
    public void test03() throws FileNotFoundException, IOException {
        ImageUtil.watermark(imageDir + "1.png", "center", imageDir + "pool.jpg", "d:\\p3.png");
    }

    @Test
    public void test04() throws FileNotFoundException, IOException {
        ImageUtil.resize(imageDir + "pool.jpg", "d:\\p4.png", 100, 100);
    }

    @Test
    public void test05() {
        String type = ImageUtil.getImageType(imageDir + "1.png");
        Assert.assertEquals("png", type);
        // 2.png 是一个假的图片，只是后缀名被改为png
        try {
            ImageUtil.getImageType(imageDir + "2.png");
        } catch (Exception e) {
            Assert.assertEquals("您输入的文件可能不是图片", e.getMessage());
        }
    }

}
