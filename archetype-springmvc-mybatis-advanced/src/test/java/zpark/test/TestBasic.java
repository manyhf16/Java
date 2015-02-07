package zpark.test;

import javax.sql.DataSource;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-config.xml")
public abstract class TestBasic {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ResourceDatabasePopulator populator;

	@Autowired
	@Qualifier("writeDataSource")
	private DataSource dataSource;

	public void initDatabase() {
		try {
			populator.populate(dataSource.getConnection());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
