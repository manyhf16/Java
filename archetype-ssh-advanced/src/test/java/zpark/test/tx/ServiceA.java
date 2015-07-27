package zpark.test.tx;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceA {

    private ServiceB serviceB;

    public void setServiceB(ServiceB serviceB) {
        this.serviceB = serviceB;
    }

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * 
     * @param stu
     * @param withException true 表示发生异常，false表示不会出现异常
     */
    public void a(Student stu, boolean withException) {
        /* 故意捕获了异常，模拟不正确的使用 */
        try {
            System.out.println("========a========");
            System.out.println(sessionFactory.getCurrentSession().getFlushMode());
            sessionFactory.getCurrentSession().save(stu);
            serviceB.b(withException);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
