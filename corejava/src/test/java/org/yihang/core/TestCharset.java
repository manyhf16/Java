package org.yihang.core;

import java.io.UnsupportedEncodingException;

public class TestCharset {
    
    public static void main(String[] args) throws UnsupportedEncodingException {
        String a = "a";
        System.out.println(a);
        byte[] b= a.getBytes("utf-16");
        System.out.println(b);
    }

}
