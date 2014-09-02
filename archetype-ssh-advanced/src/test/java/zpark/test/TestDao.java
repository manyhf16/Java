package zpark.test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.transaction.annotation.Transactional;

import zpark.dao.SampleDao;
import zpark.dao.SampleQueryBuilder;
import zpark.entity.SampleEntity;
import zpark.ext.hibernate.HibernateTemplateExt;
import zpark.ext.query.Page;

@Transactional
public class TestDao extends TestBasic {

	@Autowired
	private SampleDao dao;
	@Autowired
	private HibernateTemplateExt template;
	@Autowired
	private ApplicationContext context;
	@Test
	public void test0() {
		initDatabase();
	}

	@Test
	public void test1() {
//		initDatabase();
		SampleEntity e = new SampleEntity();
		e.setName("赵敏");
		dao.save(e);
		Long count = dao.findOne(Long.class, "select count(*) from SampleEntity");
		Assert.assertEquals(Long.valueOf(9), count);
	}

	@Test
	public void test2() {
		SampleEntity e = dao.findOne(1);
		Assert.assertEquals("赵一伤", e.getName());
	}

	@Test
	public void test3() {
		SampleEntity e = dao.findOne(2);
		e.setName("重庆");
		dao.update(e);
	}

	@Test
	public void test4() {
		SampleEntity se = dao.findOne("from SampleEntity where id = ?", 2);
		Assert.assertEquals("钱二败", se.getName());
	}

	@Test
	public void test5() {
		dao.delete(1);
		Long count = dao.findOne(Long.class, "select count(*) from SampleEntity");
		Assert.assertEquals(Long.valueOf(7), count);
	}

	@Test
	public void test6() {
		Page<SampleEntity> page = dao.find1(2, 5, 0, "%");
		Assert.assertEquals(3, page.getList().size());
		Assert.assertEquals(8, page.getTotal());
	}

	@Test
	public void test7() {
		Page<SampleEntity> page = dao.find2(1, 5, 0, "%", "id", "desc", "name", "asc");
		Assert.assertEquals(5, page.getList().size());
		Assert.assertEquals(8, page.getTotal());
		Assert.assertEquals("王八衰", page.getList().get(0).getName());
	}

	@Test
	public void test8() {
		Page<SampleEntity> page = dao.find3(1, 5, "id", "desc", new SampleQueryBuilder(null, "%"));
		Assert.assertEquals(5, page.getList().size());
		Assert.assertEquals(8, page.getTotal());
		Assert.assertEquals("王八衰", page.getList().get(0).getName());
	}
	
	@Test
	public void test9() {
		dao.find4(1, 10, Arrays.asList(1,2,3));
//		System.out.println(page.getList());
	}
	
	@Test
	public void test12() {
		dao.sample();
//		System.out.println(page.getList());
	}
	
	@SuppressWarnings("serial")
	@Test
	public void test10() {
		List<SampleEntity> list = template.findPage(1, 10, "from SampleEntity where id in :ids and name like :name", new HashMap<String,Object>(){{
			put("ids", Arrays.asList(1,2,3));
			put("name", "赵%");
		}});
		System.out.println(list);
	}
	
	@Test
	public void test11() throws NoSuchMethodException, SecurityException {
		ParameterNameDiscoverer pmd = new LocalVariableTableParameterNameDiscoverer();
		Method m = SampleDao.class.getMethod("find1", int.class, int.class, int.class, String.class);
		System.out.println(m);
		MethodParameter mp1 = new MethodParameter(m, 0);
		
		mp1.initParameterNameDiscovery(pmd);
		System.out.println(mp1.getParameterName());
		
		MethodParameter mp2 = new MethodParameter(m, 1);
		
		mp2.initParameterNameDiscovery(pmd);
		System.out.println(mp2.getParameterName());
	}

}
