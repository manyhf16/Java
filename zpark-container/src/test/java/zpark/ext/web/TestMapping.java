package zpark.ext.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class TestMapping {
    
    @Test
    public void test01() {
        String a = "^"+"/user/{id}/page/{num}" + "$";
        String b = a.replaceAll("\\{.+?\\}", "[^/=?]+?");
        System.out.println(b);
        Matcher m = Pattern.compile(b).matcher("/user/2/page/2");
        System.out.println(m.find());
    }
    
    @Test
    public void test02() {
//        Matcher m1 = Pattern.compile("^"+"/user/{id}/page/{num}" + "$".replaceAll("\\{.+?\\}", "($1)")).matcher("/user/44/page/3");
        Matcher m1 = Pattern.compile(("^"+"/user/{id}/page/{num}" + "$").replaceAll("\\{.+?\\}", "\\\\{([^/=?]+?)\\\\}")).matcher("/user/{id}/page/{num}");
//        while(m1.find()) {
//            System.out.println(m1.group(1));
//            System.out.println(m1.group(2));
//        }
        Matcher m2 = Pattern.compile(("^"+"/user/{id}/page/{num}" + "$").replaceAll("\\{.+?\\}", "([^/=?]+?)")).matcher("/user/44/page/3");
        while(m1.find() && m2.find()) {
            System.out.println(m1.groupCount());
            System.out.print(m1.group(1));  System.out.println(m2.group(1));
            System.out.print(m1.group(2));  System.out.println(m2.group(2));
        }
    }

}
