package zpark.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import zpark.dao.SampleDao;
import zpark.dao.SampleQueryBuilder;
import zpark.entity.SampleEntity;
import zpark.ext.query.Page;

@Transactional
public class TestDao extends TestBasic {

	@Autowired
	private SampleDao dao;

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
		Page<SampleEntity> page = dao.find2(1, 5, 0, "%", "id", "desc");
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

}
