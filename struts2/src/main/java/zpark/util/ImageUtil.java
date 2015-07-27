package zpark.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import org.im4java.core.CommandException;
import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.core.ImageCommand;
import org.im4java.core.Stream2BufferedImage;
import org.im4java.process.ArrayListOutputConsumer;
import org.im4java.process.ProcessStarter;

public abstract class ImageUtil {
    private static String CMD_PATH = "C:\\Program Files (x86)\\GraphicsMagick-1.3.19-Q8";
    static {
        // 指定ImageMagick 或 GraphicsMagick的安装路径
        ProcessStarter.setGlobalSearchPath(CMD_PATH);
    }

    // im4java支持ImageMagick和GraphicsMagick, true 表示使用GraphicsMagick, false
    // 表示使用ImageMagick
    public static boolean USE_GRAPHICS_MAGICK = true;

    /**
     * 截取图片
     * 
     * @param src
     *            原始图片
     * @param dist
     *            目标图片
     * @param x1
     *            左上x坐标
     * @param y1
     *            左上y坐标
     * @param width
     *            宽
     * @param height
     *            高
     */
    public static void crop(String src, String dest, int x1, int y1, int width, int height) {
        ConvertCmd cmd = new ConvertCmd(USE_GRAPHICS_MAGICK);
        IMOperation op = new IMOperation();
        op.addImage(src);
        op.crop(width, height, x1, y1);
        op.addImage(dest);
        runCmd(cmd, op);
    }

    /**
     * 截取图片
     * 
     * @param src
     *            原始图片
     * @param x1
     *            左上x坐标
     * @param y1
     *            左上y坐标
     * @param width
     *            宽
     * @param height
     *            高
     * @return 表示内存图片的BufferedImage对象
     */
    public static BufferedImage crop(String src, int x1, int y1, int width, int height) {
        ConvertCmd cmd = new ConvertCmd(USE_GRAPHICS_MAGICK);
        IMOperation op = new IMOperation();
        op.addImage(src);
        op.crop(width, height, x1, y1);
        return getBufferedImage(cmd, op);
    }

    /**
     * 添加图片水印
     * 
     * @param watermark
     *            水印图片路径
     * @param gravity
     *            水印图片的位置 gravity 的取值有NorthWest, North, NorthEast, West, Center,
     *            East,
     * @param src
     *            原始图片
     * @param dest
     *            目标图片
     */
    public static void watermark(String watermark, String gravity, String src, String dest) {
        CompositeCmd cmd = new CompositeCmd(USE_GRAPHICS_MAGICK);
        IMOperation op = new IMOperation();
        op.addImage(watermark);
        // dissolve 水印图片的溶解度
        // SouthWest, South, SouthEast
        op.dissolve(25).gravity(gravity);
        op.addImage(src);
        op.addImage(dest);
        runCmd(cmd, op);
    }

    /**
     * 添加图片水印
     * 
     * @param watermark
     *            水印图片路径
     * @param gravity
     *            水印图片的位置 gravity 的取值有NorthWest, North, NorthEast, West, Center,
     *            East,
     * @param src
     *            原始图片
     * @return 表示内存图片的BufferedImage对象
     */
    public static BufferedImage watermark(String watermark, String gravity, String src) {
        CompositeCmd cmd = new CompositeCmd(USE_GRAPHICS_MAGICK);
        IMOperation op = new IMOperation();
        op.addImage(watermark);
        // dissolve 水印图片的溶解度
        // SouthWest, South, SouthEast
        op.dissolve(25).gravity(gravity);
        op.addImage(src);
        return getBufferedImage(cmd, op);
    }

    /**
     * 改变大小（在保持图片宽高比的情况下，选择width和height中较大的）
     * 
     * @param src
     *            原始图片
     * @param dist
     *            目标图片
     * @param width
     * @param height
     */
    public static void resize(String src, String dest, int width, int height) {
        ConvertCmd cmd = new ConvertCmd(USE_GRAPHICS_MAGICK);
        IMOperation op = new IMOperation();
        op.addImage(src);
        op.resize(width, height);
        op.addImage(dest);
        runCmd(cmd, op);
    }

    /**
     * 改变大小（在保持图片宽高比的情况下，选择width和height中较大的）, 返回内存图片，而非输出至外部文件
     * 
     * @param src
     *            原始图片
     * @param width
     * @param height
     * @return 表示内存图片的BufferedImage对象
     */
    public static BufferedImage resize(String src, int width, int height) {
        ConvertCmd cmd = new ConvertCmd(USE_GRAPHICS_MAGICK);
        IMOperation op = new IMOperation();
        op.addImage(src);
        op.resize(width, height);
        return getBufferedImage(cmd, op);
    }

    private static BufferedImage getBufferedImage(ImageCommand cmd, IMOperation op) {
        op.addImage("png:-");
        Stream2BufferedImage s2b = new Stream2BufferedImage();
        cmd.setOutputConsumer(s2b);
        runCmd(cmd, op);
        return s2b.getImage();
    }

    private static void runCmd(ImageCommand cmd, IMOperation op) {
        try {
            cmd.run(op);
        } catch (Exception e) {
            if (e instanceof CommandException) {
                Throwable t = e.getCause();
                if (t != null && "gm".equals(t.getMessage())) {
                    throw new RuntimeException("没有正确安装ImageMagick 或 GraphicsMagick", e);
                }
            }
            throw new RuntimeException("运行ImageMagick 发生错误", e);
        }
    }

    /**
     * 检查图片文件类型，如果不是图片抛出异常
     * 
     * @param src
     * @return 图片类型
     */
    public static String getImageType(String src) {
        try {
            IMOperation op = new IMOperation();
            op.format("%m"); // 请参考：http://www.imagemagick.org/script/escape.php
            op.addImage(src);
            IdentifyCmd cmd = new IdentifyCmd(true);
            ArrayListOutputConsumer output = new ArrayListOutputConsumer();
            cmd.setOutputConsumer(output);
            cmd.run(op);
            return output.getOutput().get(0).toLowerCase();
        } catch (Exception e) {
            throw new RuntimeException("您输入的文件可能不是图片", e);
        }
    }

    /**
     * 为struts 提供的工具方法，返回图片对象对应的输入流和大小. 注意: 这种方法会占用较多的内存，实际项目中请根据情况选择其它实现手段
     * 
     * @param image
     * @return
     */
    public static ImageInfo getImageInfo(BufferedImage image) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream(2048);
            ImageIO.write(image, "png", os);
            byte[] imageBytes = os.toByteArray();
            ImageInfo info = new ImageInfo(new ByteArrayInputStream(imageBytes), imageBytes.length);
            return info;
        } catch (IOException e) {
            throw new RuntimeException("处理图片发生错误", e);
        }
    }

    public static void saveBase64Image(String base64Str) {
        // 处理空图片
        if("data:,".equals(base64Str)){
            return;
        }
        FileOutputStream os = null;
        try {
            byte[] bytes = Base64.decodeBase64(base64Str.replaceAll("data:image/png;base64,", ""));
            String md5 = DigestUtils.md5Hex(bytes);
            String dest = ServletActionContext.getServletContext().getRealPath("/temp") + File.separator + md5 + ".png";
            System.out.println(dest);
            os = new FileOutputStream(dest);
            os.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
