package zpark.ext.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashFunction {

    public Integer hash(String string) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(string.getBytes());
            byte[] md5 = digest.digest();
            // 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
            // 098f6bcd4621 d373ca de 4e 83 26 27 b4 f6
            // 098f6bcd -> 只取低32位 -> Integer 160394189
            int result = ((int) (md5[0] & 0xFF) << 24) + ((int) (md5[1] & 0xFF) << 16) + ((int) (md5[2] & 0xFF) << 8)
                    + ((int) (md5[3] & 0xFF));
            return result;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // public static void main(String[] args) {
    // int a = new HashFunction().hash("test");
    // System.out.println(a);
    // }

}
