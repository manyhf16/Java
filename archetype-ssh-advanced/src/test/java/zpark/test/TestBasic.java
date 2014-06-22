package zpark.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-config.xml")
public abstract class TestBasic {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ResourceDatabasePopulator populator;

	public void initDatabase() {
		try {
			AnnotationSessionFactoryBean sessionFactoryBean = (AnnotationSessionFactoryBean) applicationContext.getBean("&sessionFactory");
			sessionFactoryBean.dropDatabaseSchema();
			sessionFactoryBean.createDatabaseSchema();
			populator.populate(sessionFactoryBean.getDataSource().getConnection());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
