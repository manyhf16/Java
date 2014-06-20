package zpark.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import zpark.dao.SampleDao;
import zpark.dao.SampleQueryBuilder;
import zpark.entity.SampleEntity;
import zpark.ext.hibernate.Page;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-config.xml")
@Transactional
public class TestDao {

	@Autowired
	private SampleDao dao;

	@Test
	@Rollback(false)
	public void test1() {
		SampleEntity e1 = new SampleEntity();
		e1.setName("北京");

		SampleEntity e2 = new SampleEntity();
		e2.setName("天津");
		dao.save(e1);
		dao.save(e2);
	}

	@Test
	public void test2() {
		SampleEntity e = dao.findOne(1);
		Assert.assertEquals("北京", e.getName());
	}

	@Test
	public void test3() {
		SampleEntity se = dao.findOne("from SampleEntity where id = ?", 2);
		System.out.println(se.getName());
	}

	@Test
	@Rollback(false)
	public void test4() {
		SampleEntity e = dao.findOne(2);
		e.setName("重庆");
		dao.update(e);
	}

	@Test
	@Rollback(false)
	public void test5() {
		dao.delete(1);
	}
	
	@Test
	public void test6() {
		Page<SampleEntity> page = dao.find1(2, 2, 1, "%%");
		System.out.println(page);
	}
	
	@Test
	public void test7() {
		Page<SampleEntity> page = dao.find2(1, 2, 1, "%", "id", "desc");
		System.out.println(page);
	}
	
	@Test
	public void test8() {
		Page<SampleEntity> page = dao.find3(1, 2, "id", "desc", new SampleQueryBuilder(null, "%"));
		System.out.println(page);
	}

}
