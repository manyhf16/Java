package zpark.struts2.action.day5;

import java.lang.reflect.Member;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;
import ognl.TypeConverter;

import org.junit.Assert;
import org.junit.Test;

import zpark.entity.City;
import zpark.entity.Person;

public class TestOgnl {

    @Test
    public void test1() throws OgnlException {
        Person target = new Person();
        target.setName("张三");
        Assert.assertEquals("张三", Ognl.getValue("name", target));

        Ognl.setValue("name", target, "李四");
        Assert.assertEquals("李四", target.getName());
    }

    @Test
    public void test2() throws OgnlException {
        Person target = new Person();
        target.setName("张三");
        City city = new City();
        city.setName("北京");
        target.setCity(city);

        // 相当于: target.getCity().getName();
        Assert.assertEquals("北京", Ognl.getValue("city.name", target));

        // 相当于: target.getCity().setName("南京");
        Ognl.setValue("city.name", target, "南京");
        Assert.assertEquals("南京", target.getCity().getName());
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void test3() throws OgnlException {
        Assert.assertTrue(java.util.Date.class.isAssignableFrom(java.sql.Date.class));
        Map context = Ognl.createDefaultContext(null, null, new TypeConverter() {

            @Override
            public Object convertValue(Map context, Object target, Member member, String propertyName, Object value,
                    Class toType) {
                // 如果是java.util.Date或它的子类
                if (java.util.Date.class.isAssignableFrom(toType) && value instanceof String) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        return sdf.parse(value.toString());
                    } catch (ParseException e) {
                        // 忽略处理转换失败
                        return null;
                    }
                }
                return null;
            }
        });
        Person target = new Person();
        Ognl.setValue("birthday", context, target, "2001-5-1");
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(Calendar.YEAR, 2001);
        c.set(Calendar.MONTH, 4);
        c.set(Calendar.DATE, 1);
        Assert.assertEquals(c.getTime(), target.getBirthday());
    }

    @Test
    public void test4() throws OgnlException {
        List<String> list = new ArrayList<String>();
        list.add("北京");
        list.add("上海");
        Assert.assertEquals("上海", Ognl.getValue("[1]", list));
        Assert.assertEquals(2, Ognl.getValue("size", list));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void test5() throws OgnlException {
        Map context = Ognl.createDefaultContext(null);
        Person target = new Person();
        target.setName("张三");
        context.put("everything", "ok");
        Assert.assertEquals("张三", Ognl.getValue("name", context, target));
        Assert.assertEquals("ok", Ognl.getValue("#everything", context, target));

        Ognl.setValue("#everything", context, target, "not ok");
        Assert.assertEquals("not ok", context.get("everything"));
    }

}
