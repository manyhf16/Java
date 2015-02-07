package zpark.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zpark.dao.ProductDao;
import zpark.entity.Product;

public class TestGenericDao extends TestBasic {
	
	@Autowired
	private ProductDao productDao;
	
	@Test
	public void test1() {
//		super.initDatabase();
		List<Product> page = productDao.findByName("iPhone", Arrays.asList(1000,2000));
		System.out.println(page);
	}

}
