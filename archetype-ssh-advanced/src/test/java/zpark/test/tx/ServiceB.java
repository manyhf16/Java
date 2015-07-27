package zpark.test.tx;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceB {
    
    @Autowired
    private SessionFactory sessionFactory;
	
	@SuppressWarnings({ "unused", "unchecked" })
    public void b(boolean withException) {
		System.out.println("========b========");
		System.out.println(sessionFactory.getCurrentSession().getFlushMode());
		List<Student> list = sessionFactory.getCurrentSession().createQuery("from Student").list();
		for(Student s : list) {
		    s.setName("lisi");
		}
		if(withException) {
		    int i = 1/0;
		}
	}

}
