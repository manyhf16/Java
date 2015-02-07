package zpark.test;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zpark.entity.Category;
import zpark.entity.SampleEntity;

public class TestCache extends TestBasic {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Test
	public void test1() {
		Session session = sessionFactory.openSession();
		SampleEntity se = (SampleEntity)session.get(SampleEntity.class, 1);
		System.out.println(se.getName());
		session.close();
		session = sessionFactory.openSession();
		se = (SampleEntity) session.get(SampleEntity.class, 1);
		System.out.println(se.getName());
	}
	
	@Test
	public void test2() {
		Session session = sessionFactory.openSession();
		Category se = (Category)session.get(Category.class, 1);
		System.out.println(se.getName());
		System.out.println(se.getProducts().size());
		session.close();
		session = sessionFactory.openSession();
		se = (Category)session.get(Category.class, 1);
		System.out.println(se.getName());
		System.out.println(se.getProducts().size());
	}
	
	@Test
	public void test3() {
		Session session = sessionFactory.openSession();
		session.createQuery("from Category").setCacheable(true).list();
		
		Session s2 = sessionFactory.openSession();
		s2.beginTransaction();
		Category c = new Category();
		c.setId(1);
		c.setName("电子产品2");
		s2.update(c);
		s2.getTransaction().commit();
		
		session.createQuery("from Category").setCacheable(true).list();
//		session.createQuery("from Category").setCacheable(true).list();
//		session.createQuery("from Category").setCacheable(true).list();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test4() {
//		initDatabase();
		Session session = sessionFactory.openSession();
//		List<Category> list = session.createCriteria(Category.class)
//				.setFetchMode("products", FetchMode.JOIN).list();
//		List<Category> list = session.createCriteria(Category.class).createAlias("products", "p", CriteriaSpecification.LEFT_JOIN).list();
		List<Category> list = session.createQuery("select distinct c from Category c left join fetch c.products p where p.price > 3000 order by c.id")
				.list();
		for(Category c : list) {
			System.out.println(c.getName());
			System.out.println(c.getProducts());
		}
	}

}
