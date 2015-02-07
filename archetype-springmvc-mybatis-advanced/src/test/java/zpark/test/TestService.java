package zpark.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import zpark.entity.Classmate;
import zpark.entity.Location;
import zpark.service.ClassmateService;

public class TestService extends TestBasic {

	@Autowired
	private ClassmateService classmateService;
	
	@Test
	public void test00() {
		initDatabase();
	}
	
	@Test @Transactional
	public void test01() {
		Classmate c = classmateService.findByUsername("manyihang");
		Location location = new Location();
		location.setName("重庆");
		c.setLocation(location);
		List<String> tags = Arrays.asList("内向", "没出息", "胆小"); // 此顺序符合数据库中的顺序
		c.setTags(tags);
		classmateService.update(c);
		Assert.assertEquals(13, classmateService.findAllLocations().size());
		System.out.println(classmateService.findByUsername("manyihang").getTags());
		Assert.assertArrayEquals(new String[]{"内向", "没出息", "胆小"}, classmateService.findByUsername("manyihang").getTags().toArray());
	}
}
