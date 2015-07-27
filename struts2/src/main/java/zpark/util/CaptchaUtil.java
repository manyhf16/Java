package zpark.util;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.engine.image.gimpy.SimpleListImageCaptchaEngine;
import com.octo.captcha.service.captchastore.CaptchaStore;
import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

public abstract class CaptchaUtil {

    private static ImageCaptchaService imageCaptchaService;

    static {

        CaptchaStore captchaStore = new FastHashMapCaptchaStore();

        CaptchaEngine captchaEngine = //new MyImageCaptchaEngine();
         new SimpleListImageCaptchaEngine();
        // new DefaultGimpyEngine();
        imageCaptchaService = new DefaultManageableImageCaptchaService(captchaStore, captchaEngine, 180, 100000, 75000);
    }

    public static ImageCaptchaService getService() {
        return imageCaptchaService;
    }

	/**
	用来产生随机的验证码图片对象
	*/
    public static BufferedImage getImage(String sessionId) {
        return imageCaptchaService.getImageChallengeForID(sessionId);
    }

	/**
	验证验证码是否正确
	*/
    public static boolean validateImage(String sessionId, Object captcha) {
        return imageCaptchaService.validateResponseForID(sessionId, captcha);
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        ImageIO.write(CaptchaUtil.getImage("aaa"), "png", new FileOutputStream("d:/1.png"));
    }

}
