package zpark.test;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import zpark.action.SampleAction;
import zpark.dao.CategoryDao;
import zpark.dao.ProductDao;
import zpark.entity.Product;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate3.Hibernate3Module;
import com.fasterxml.jackson.datatype.hibernate3.Hibernate3Module.Feature;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-config.xml")
@Transactional
public class TestAction {

	@Autowired
	private SampleAction action;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Test
	public void test1() {
		String view = action.execute();
		Assert.assertEquals("input", view);
	}
	
	@Test
	public void test2() {
		action.setName("not null");
		String view = action.execute();
		Assert.assertEquals("success", view);
	}
	
	@Test
	public void test3() throws JsonGenerationException, JsonMappingException, IOException {
//		SampleAction action = new SampleAction();
		action.setResult("aaa");
		action.setName("ok");
		ObjectMapper om = new ObjectMapper();
		StringWriter sw = new StringWriter();
		om.writeValue(sw, action);
		System.out.println(sw.toString());
	}
	
//	@Before
//	@Rollback(false)
//	public void before() {
//		Product p = new Product();
//		p.setName("iPad");
//		
//		Category c = new Category();
//		c.setName("电子产品");
//		p.setCategory(c);
//		categoryDao.save(c);
//		productDao.save(p);		
//	}
	
	@Test
	public void test4 () throws JsonGenerationException, JsonMappingException, IOException {
		Product e = productDao.findOne(1);
		ObjectMapper om = new ObjectMapper();
		Hibernate3Module hibernate3Module = new Hibernate3Module();
		hibernate3Module.enable(Feature.FORCE_LAZY_LOADING);
		om.registerModule(hibernate3Module);		
		StringWriter sw = new StringWriter();
		om.writeValue(sw, e);
		System.out.println(sw.toString());
	}
	
}
