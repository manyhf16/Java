package zpark.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import zpark.entity.Category;
import zpark.entity.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-config.xml")
public class TestSessionFactory {

	@Autowired
	private SessionFactory factory;
	
	@Test
	public void test1() {
		Session session = factory.openSession();
		Product p = (Product) session.get(Product.class, 1);
		Product p2 = new Product();
		p2.setName("iPhone");
		System.out.println(p);
		session.close();
		session = factory.openSession();
		session.beginTransaction();
		Category c = (Category) session.get(Category.class, 1);
		c.getProducts().add(p);
		c.getProducts().add(p2); p2.setCategory(c);
		System.out.println(c.getProducts());
		session.persist(c);
		session.getTransaction().commit();
		session.close();
	}
	
	@Test
	public void test2() {
		Session session = factory.openSession();
		Product p = new Product();
		p.setId(1);
		p.setName("ccc");
		session.persist(p);
		System.out.println(p.getId());
		session.beginTransaction();
		session.getTransaction().commit();
		System.out.println(p.getId());
		session.close();
	}
	
}
