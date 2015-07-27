package zpark.struts2.action.day2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

public class TestDate {

    @Test
    public void test() {
        Locale locale = Locale.CHINA; // 指国家和地区信息
        DateFormat dt1 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG, locale);
        DateFormat dt2 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, locale);
        DateFormat dt3 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);

        DateFormat d1 = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        DateFormat d2 = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        DateFormat d3 = DateFormat.getDateInstance(DateFormat.LONG, locale);

        DateFormat rfc3399 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        System.out.println(dt1.format(new Date()));
        System.out.println(dt2.format(new Date()));
        System.out.println(dt3.format(new Date()));
        System.out.println(d1.format(new Date()));
        System.out.println(d2.format(new Date()));
        System.out.println(d3.format(new Date()));
        System.out.println(rfc3399.format(new Date()));
    }

}
