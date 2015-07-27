package zpark.test.tx;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:zpark/test/tx/test-tx.xml")
public class TestTransaction {

    @Autowired
    private ServiceA a;

    @Autowired
    private ServiceB b;

    @Resource(name = "required_required")
    private TransactionInterceptor required_required;

    @Resource(name = "supports_required")
    private TransactionInterceptor supports_required;

    @Resource(name = "required_requires_new")
    private TransactionInterceptor required_requires_new;

    @Resource(name = "required_supports_readonly")
    private TransactionInterceptor required_supports_readonly;

    private Student s;

    @Autowired
    private SessionFactory sessionFactory;

    @Before
    public void before() {
        s = new Student();
        s.setId(1);
        s.setName("zhangsan");
    }

    @Test
    public void test0() {
        /*
         * hibernate 自动flush仅发生在事务环境下(即设置了手动提交)，
         * 而手动调用flush在没有事务情况下仍然可以执行增删改操作(即使用默认的自动提交)
         */
        Session session = sessionFactory.openSession();
        session.save(s);
        session.flush();
        Assert.assertEquals(1, session.createQuery("from Student").list().size());
        session.close();
    }

    @Test
    public void test1() {
        /* b方法应当回滚, 但a方法本应提交, 矛盾导致出现 UnexpectedRollbackException */
        try {
            AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
            advisor.setExpression("within(zpark.test.tx.*)");
            advisor.setAdvice(required_required);
            ServiceA a = getServiceA(advisor);
            a.a(s, true);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof UnexpectedRollbackException);
        }
    }

    @Test
    public void test2() {
        /*
         * b方法应当回滚, a方法没有事务 但注意虽然a方法设置为supports， 但由于a方法内异常被捕获，并且没有设置readonly.
         * 因此相当于事务采用了自动提交，仍会导致数据插入
         */
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression("within(zpark.test.tx.*)");
        advisor.setAdvice(supports_required);
        ServiceA a = getServiceA(advisor);
        a.a(s, true);
    }

    @Test
    public void test3() {
        /* b方法应当回滚, a方法应当提交 */
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression("within(zpark.test.tx.*)");
        advisor.setAdvice(required_requires_new);
        ServiceA a = getServiceA(advisor);
        a.a(s, true);
    }

    @Test
    public void test4() {
        /*
         * supports表示加入当前事务，虽然在b方法上添加了readonly，但实际仍然使用a的事务设置，flushMode仍然是Auto
         */
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression("within(zpark.test.tx.*)");
        advisor.setAdvice(required_supports_readonly);
        ServiceA a = getServiceA(advisor);
        a.a(s, false);
    }

    @Test
    public void test5() {
        /* 直接调用b, readonly导致flushMode设置为Manual，导致不会执行数据更新 */
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression("within(zpark.test.tx.*)");
        advisor.setAdvice(required_supports_readonly);
        ServiceB b = getServiceB(advisor);
        b.b(false);
    }

    private ServiceA getServiceA(AspectJExpressionPointcutAdvisor advisor) {
        ProxyFactoryBean factoryA = new ProxyFactoryBean();
        factoryA.setProxyTargetClass(true);
        factoryA.setTarget(a);
        factoryA.addAdvisor(advisor);

        ProxyFactoryBean factoryB = new ProxyFactoryBean();
        factoryB.setProxyTargetClass(true);
        factoryB.setTarget(b);
        factoryB.addAdvisor(advisor);

        ServiceA proxyA = (ServiceA) factoryA.getObject();
        ServiceB proxyB = (ServiceB) factoryB.getObject();
        proxyA.setServiceB(proxyB);
        return proxyA;
    }

    private ServiceB getServiceB(AspectJExpressionPointcutAdvisor advisor) {
        ProxyFactoryBean factoryB = new ProxyFactoryBean();
        factoryB.setProxyTargetClass(true);
        factoryB.setTarget(b);
        factoryB.addAdvisor(advisor);
        ServiceB proxyB = (ServiceB) factoryB.getObject();
        return proxyB;
    }

}
