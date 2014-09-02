package zpark.test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import zpark.action.SampleAction;
import zpark.dao.ProductDao;
import zpark.entity.Product;
import zpark.entity.SampleEntity;

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
	private ProductDao dao;
	
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
		List<SampleEntity> result = new ArrayList<SampleEntity>();
		SampleEntity s1 = new SampleEntity();
		s1.setId(1);
		s1.setName("张三");
		SampleEntity s2 = new SampleEntity();
		s2.setId(2);
		s2.setName("李四");
		result.add(s1);
		result.add(s2);
		action.setResult(result);
		action.setName("ok");
		ObjectMapper om = new ObjectMapper();
		StringWriter sw = new StringWriter();
		om.writeValue(sw, action);
		Assert.assertEquals("{\"name\":\"ok\",\"result\":{\"value\":[{\"id\":1,\"name\":\"张三\"},{\"id\":2,\"name\":\"李四\"}]}}", sw.toString());
	}
	
	@Test
	public void test4 () throws JsonGenerationException, JsonMappingException, IOException {
		Product e = dao.findOne(1);
		ObjectMapper om = new ObjectMapper();
		Hibernate3Module hibernate3Module = new Hibernate3Module();
		hibernate3Module.enable(Feature.FORCE_LAZY_LOADING);
		om.registerModule(hibernate3Module);		
		StringWriter sw = new StringWriter();
		om.writeValue(sw, e);
		System.out.println(sw.toString());
		Assert.assertEquals("{\"id\":1,\"name\":\"iPad\",\"category\":{\"id\":1,\"name\":\"电子产品\"}}", sw.toString());
	}
	
}
