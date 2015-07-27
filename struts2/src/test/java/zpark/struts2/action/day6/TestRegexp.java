package zpark.struts2.action.day6;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class TestRegexp {

    @Test
    public void test1() throws UnsupportedEncodingException {
        Pattern p = Pattern.compile("^\\(\\d{3}\\) \\d{4}-\\d{5}$");
        Matcher m = p.matcher("(086) 1234-56789");
        Assert.assertTrue(m.matches());
    }
    
    @Test
    public void test2() {
        Matcher m = Pattern.compile("day\\d{1,2}").matcher("/struts2/day7/");
        m.find();
        Assert.assertEquals("day7", m.group());
    }

}
