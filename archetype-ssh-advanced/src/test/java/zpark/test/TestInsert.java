package zpark.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zpark.service.CategoryService;

public class TestInsert extends TestBasic {
	
	@Autowired
	private CategoryService service ;
	
	@Test
	public void test1() {
		service.insert(null);
	}

}
