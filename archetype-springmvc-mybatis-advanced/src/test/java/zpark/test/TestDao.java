package zpark.test;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import zpark.dao.ClassmateDao;
import zpark.entity.Classmate;
import zpark.entity.Constellation;
import zpark.entity.Location;

public class TestDao extends TestBasic {

	@Autowired
	private ClassmateDao dao;

	@Test
	public void test00() {
		// initDatabase();
	}

	@Test
	public void test01() {
		List<Classmate> list = dao.findAll();
		list = dao.findAll();
		list = dao.findAll();
//		Assert.assertEquals(47, list.size());
	}

	@Test
	public void test02() {
		List<Classmate> list = dao.findByGender("F");
		Assert.assertEquals(23, list.size());
	}

	@Test
	public void test03() {
		List<Classmate> list = dao.findAllLost();
		Assert.assertEquals(12, list.size());
	}

	@Test
	public void test04() {
		List<Classmate> list = dao.findByYear(1976);
		Assert.assertEquals(26, list.size());
	}

	@Test
	public void test05() {
		List<Classmate> list = dao.findByMonth(12);
		Assert.assertEquals(5, list.size());
	}

	@Test
	public void test06() {
		List<Classmate> list = dao.findByYearAndMonth(1976, 12);
		Assert.assertEquals(3, list.size());
	}

	@Test
	public void test07() {
		List<Classmate> list = dao.findByConstellation(Constellation.射手座.toString());
		Assert.assertEquals(4, list.size());
	}

	@Test
	public void test08() {
		List<Classmate> list = dao.findByLocation(2);
		Assert.assertEquals(7, list.size());
	}

	@Test
	public void test09() {
		List<Classmate> list = dao.findByTag("大龙");
		Assert.assertEquals(1, list.size());
		list = dao.findByTag("大龙");
	}

	@Test
	public void test10() throws UnsupportedEncodingException {
		Classmate c = dao.findById(54);
		String md5 = DigestUtils.md5DigestAsHex("123456".getBytes("utf-8"));
		Assert.assertEquals("lixiaolong", c.getUsername());
		Assert.assertEquals(md5, c.getPassword());
	}

	@Test
	public void test11() throws UnsupportedEncodingException {
		Classmate c = dao.findByUsername("lixiaolong");
		String md5 = DigestUtils.md5DigestAsHex("123456".getBytes("utf-8"));
		Assert.assertEquals(md5, c.getPassword());
	}

	@Test
	public void test12() {
		List<Classmate> list = dao.findByName("李");
		Assert.assertEquals(5, list.size());
	}

	@Test
	@Transactional
	public void test13() {
		dao.deleteTags("lixiaolong");
		Classmate c = dao.findByUsername("lixiaolong");
		Assert.assertEquals(0, c.getTags().size());
	}

	@Test
	@Transactional
	public void test14() {
		dao.insertTags("lixiaolong", Arrays.asList("沫子王"));
		Classmate c = dao.findByUsername("lixiaolong");
		Assert.assertEquals(3, c.getTags().size());
	}

	@Test
	@Transactional
	public void test15() throws UnsupportedEncodingException {
		dao.updatePassword(54, "654321");
		Classmate c = dao.findByUsername("lixiaolong");
		String md5 = DigestUtils.md5DigestAsHex("654321".getBytes("utf-8"));
		Assert.assertEquals(md5, c.getPassword());
	}

	@Test
	@Transactional
	public void test16() throws UnsupportedEncodingException {
		Classmate classmate = dao.findById(54);
		classmate.setMobile("13811112222");
		dao.update(classmate);
		Classmate c = dao.findById(54);
		Assert.assertEquals("13811112222", c.getMobile());
	}

	@Test
	public void test17() {
		Location location = dao.findLocationById(1);
		Assert.assertEquals("区内", location.getName());
		Assert.assertNull(dao.findLocationById(13));
	}

	@Test
	@Transactional
	public void test18() {
		Location location = new Location();
		location.setName("香港");
		dao.insertLocation(location);
		List<Location> list = dao.findAllLocations();
		Assert.assertEquals(13, list.size());
	}

	@Test
	public void test19() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = sdf.parse("1976-1-19");
		String constellation = dao.findConstellation(d);
		Assert.assertEquals(Constellation.魔羯座.toString(), constellation);

		d = sdf.parse("1977-1-1");
		constellation = dao.findConstellation(d);
		Assert.assertEquals(Constellation.魔羯座.toString(), constellation);
		
		d = sdf.parse("1976-12-23");
		constellation = dao.findConstellation(d);
		Assert.assertEquals(Constellation.魔羯座.toString(), constellation);
	}

}
