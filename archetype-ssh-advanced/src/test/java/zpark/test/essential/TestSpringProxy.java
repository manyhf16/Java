package zpark.test.essential;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.interceptor.SimpleTraceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

public class TestSpringProxy {

    @Test
    public void test() {
        GenericApplicationContext context = new GenericApplicationContext();
        BeanDefinition bd = BeanDefinitionBuilder.rootBeanDefinition(C1.class).getBeanDefinition();
        context.registerBeanDefinition("test.c1", bd);

        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        creator.setBeanFactory(context.getBeanFactory());

        context.getBeanFactory().addBeanPostProcessor(creator);

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("within(zpark.test.essential.C1)");
        context.getBeanFactory().registerSingleton("advisor",
                new DefaultPointcutAdvisor(pointcut, new SimpleTraceInterceptor()));
        System.out.println(context.getBean("test.c1").getClass());
        C1 c1 = (C1) context.getBean("test.c1");
        c1.test();

        context.close();
    }

    @Test
    public void test2() {
        /* classpath*: 可以用来让同包同名的Resource 全部加载 */
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath*:zpark/test/essential/test_*.xml");
        String[] sorted = getSortedNames(beanFactory.getBeanDefinitionNames());
        Assert.assertArrayEquals(new String[] { "c1", "c2", "c3" }, sorted);

        /* 验证别名 */
        sorted = getSortedNames(beanFactory.getAliases("c3"));
        Assert.assertArrayEquals(new String[] { "c4", "c5" }, sorted);
    }

    @Test
    public void test3() {
        /* classpath: 同包同名的Resource 仅能加载找到的第一个 */
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:zpark/test/essential/test_*.xml");
        String[] sorted = getSortedNames(beanFactory.getBeanDefinitionNames());
        Assert.assertArrayEquals(new String[] { "c1", "c3" }, sorted);

        /* 测试依赖注入 */
        BeanDefinition bd = BeanDefinitionBuilder.rootBeanDefinition(C6.class).addPropertyReference("c1", "c1")
                .getBeanDefinition();
        beanFactory.registerBeanDefinition("c6", bd);
        System.out.println(Arrays.toString(beanFactory.getBeanDefinitionNames()));
        C6 c6 = (C6) beanFactory.getBean("c6");
        C1 c1 = (C1) beanFactory.getBean("c1");
        Assert.assertEquals(c1, c6.getC1());
    }
    
    private String[] getSortedNames(String[] names) {
        String[] sorted = new String[names.length];
        System.arraycopy(names, 0, sorted, 0, names.length);
        Arrays.sort(sorted);
        return sorted;
    }

}

class C1 {

    public void test() {
        System.out.println("c1...");
    }

}

class C2 {

    public void test() {
        System.out.println("c2...");
    }

}

class C3 {

    public void test() {
        System.out.println("c3...");
    }

}

class C6 {
    private C1 c1;

    public void setC1(C1 c1) {
        this.c1 = c1;
    }

    public C1 getC1() {
        return c1;
    }

    public void test() {
        System.out.println("c6..." + c1.toString());
    }
}