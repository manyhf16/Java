package zpark.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {
    private static final HanyuPinyinOutputFormat DEFAULT_FORMAT;
    static {
        DEFAULT_FORMAT = new HanyuPinyinOutputFormat();
        DEFAULT_FORMAT.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        DEFAULT_FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        DEFAULT_FORMAT.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    public static String toPinyin(String str) {
        try {
            if (str == null) {
                return "";
            }
            StringBuilder sb = new StringBuilder(16);
            for (int i = 0; i < str.length(); i++) {
                String[] array = PinyinHelper.toHanyuPinyinStringArray(str.charAt(i), DEFAULT_FORMAT);
                if (array != null) {
                    sb.append(array[0]);
                } else {
                    sb.append(str.charAt(i));
                }
            }
            return sb.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
            return "";
        }
    }
}
