package zpark.ext.web;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TestGeneric {

    private Map<String, Integer> map = new HashMap<String, Integer>();

    private List<String> list = new ArrayList<String>();
    
    public void test(List<String> list) {
        
    }
    
    @Test
    public void test04() throws NoSuchMethodException, SecurityException {
        Method m = TestGeneric.class.getMethod("test", List.class);
        System.out.println(m);
        Type type = m.getGenericParameterTypes()[0];
        System.out.println(type);
        System.out.println(type instanceof ParameterizedType);
        if(type instanceof ParameterizedType) {
            ParameterizedType t = (ParameterizedType) type;
            System.out.println((Class)t.getActualTypeArguments()[0]);
        }
    }
    
    @Test
    public void test03() {
        String[] a = new String[0];
        String[] b = new String[0];
        System.out.println(a.equals(b));
    }

    @Test
    public void test02() throws NoSuchFieldException, SecurityException {
        Class<TestGeneric> class1 = TestGeneric.class;
        Field f = class1.getDeclaredField("list");
        ParameterizedType type = (ParameterizedType) f.getGenericType();
        System.out.println(type.getActualTypeArguments()[0]);
    }

    @Test
    public void test01() throws NoSuchFieldException, SecurityException {
        // 获取Class实例
        Class<TestGeneric> class1 = TestGeneric.class;
        // 根据属性名取得该属性对应的Field对象
        Field mapField = class1.getDeclaredField("map");
        // 示范第一个方法：直接通过getType()取出Field的类型，只对普通类型的Field有效
        Class<?> class2 = mapField.getType();
        // 输出查看
        System.out.println("属性名为map的属性类型为：" + class2);

        // 示范第二种方法：
        Type mapMainType = mapField.getGenericType();
        // 为了确保安全转换，使用instanceof
        if (mapMainType instanceof ParameterizedType) {
            // 执行强制类型转换
            ParameterizedType parameterizedType = (ParameterizedType) mapMainType;
            // 获取基本类型信息，即Map
            Type basicType = parameterizedType.getRawType();
            System.out.println("基本类型为：" + basicType);
            // 获取泛型类型的泛型参数
            Type[] types = parameterizedType.getActualTypeArguments();
            for (int i = 0; i < types.length; i++) {
                System.out.println("第" + (i + 1) + "个泛型类型是：" + types[i]);
            }
        } else {
            System.out.println("获取泛型类型出错!");
        }
    }

}
