package zpark.struts2.action.day8;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestDate {

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date begin = sdf.parse("2015-3-13");
        Calendar current = Calendar.getInstance();
        current.setTime(begin); // 设置开始时间
        Date end = sdf.parse("2015-4-2"); // 结束时间
        // 小于或等于, 如果只要小于，去掉第二个条件
        while (current.getTime().before(end) || current.getTime().equals(end)) {
            System.out.println(current.getTime());
            current.add(Calendar.DATE, 1); // 增加1天
        }
    }

}
