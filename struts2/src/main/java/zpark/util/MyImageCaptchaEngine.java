package zpark.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;

import com.jhlabs.image.WaterFilter;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.ColorGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.SimpleTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

public class MyImageCaptchaEngine extends ListImageCaptchaEngine {
    /**
     * this method should be implemented as folow :
     * <ul>
     * <li>First construct all the factories you want to initialize the gimpy
     * with</li>
     * <li>then call the this.addFactoriy method for each factory</li>
     * </ul>
     */
    protected void buildInitialFactories() {
        // 水波过滤器
        WaterFilter water = new WaterFilter();
        water.setAmplitude(3d);
        water.setAntialias(true);
        water.setPhase(20d);
        water.setWavelength(70d);
        ImageDeformation backDef = new ImageDeformationByFilters(new ImageFilter[] {});
        ImageDeformation textDef = new ImageDeformationByFilters(new ImageFilter[] {});
        ImageDeformation postDef = new ImageDeformationByFilters(new ImageFilter[] { water });
        // 控制哪些字符
        WordGenerator dictionnaryWords = new RandomWordGenerator("abcdefghigklmnopqrstuvwxyz");
        // 控制字符颜色
        ColorGenerator colorGenerator = new RandomRangeColorGenerator(new int[] { 0, 0 }, new int[] { 0, 0 },
                new int[] { 0, 255 });
        // 控制字符数量颜色
        TextPaster randomPaster = new SimpleTextPaster(5, 5, colorGenerator);
        // 控制大小和背景色
        BackgroundGenerator back = new UniColorBackgroundGenerator(100, 40, Color.white);
        // 控制字体
        FontGenerator shearedFont = new RandomFontGenerator(21, 28,
                new Font[] { new Font("TimesRoman", Font.BOLD, 25) });
        // word2image 1
        WordToImage word2image = new DeformedComposedWordToImage(shearedFont, back, randomPaster, backDef, textDef,
                postDef);
        this.addFactory(new GimpyFactory(dictionnaryWords, word2image));
    }
}