package zpark.test;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import zpark.entity.Classmate;
import zpark.entity.Location;

public class TestSession extends TestBasic {

	@Autowired
	@Qualifier("readSqlSession")
	SqlSession session;

	@Test
	public void test1() {// 测试readOnly=false的缓存,会返回对象的副本
		Location l1 = session.selectOne("findLocationById", 1);
		l1.setName("修改");

		Location l2 = session.selectOne("findLocationById", 1);
		Assert.assertEquals("修改", l1.getName());
		Assert.assertEquals("区内", l2.getName());

	}
	
	@Test
	public void test2() {
		Classmate c = session.selectOne("findById", 1);
		System.out.println(c.getName());
		System.out.println(c.getLocation().getClass());
		System.out.println(c.getLocation());
	}

}
