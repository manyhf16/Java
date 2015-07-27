package zpark.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.digest.DigestUtils;

public abstract class MD5Util {

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }
    
    public static String md5(InputStream src) {
        if(src == null) {
            return null;
        }
        try {
            return DigestUtils.md5Hex(src);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                src.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
