package zpark.struts2.action.day7;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import zpark.util.CaptchaUtil;

public class CaptchaAction2 {

    public String execute() throws IOException {
        // 获取图片验证码
        HttpSession session = ServletActionContext.getRequest().getSession();
        BufferedImage image = CaptchaUtil.getImage(session.getId());

        HttpServletResponse response = ServletActionContext.getResponse();
        // 设置响应头
        if (isDownload()) {
            response.setContentType("application/octec-stream");
            String name = new String("验证码".getBytes("utf-8"), "iso-8859-1");
            response.addHeader("Content-Disposition", "attachment;filename=\"" + name + ".png\"");
        } else {
            response.addHeader("Cache-Control", "no-cache");
            response.addHeader("Cache-Control", "no-store");
            response.setDateHeader("expires", 0L);
            response.setContentType("image/png");
        }

        // 获取响应输出流
        ServletOutputStream os = response.getOutputStream();
        // 写入图片验证码
        ImageIO.write(image, "png", os);
        return null;
    }

    private boolean download;

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }

}
