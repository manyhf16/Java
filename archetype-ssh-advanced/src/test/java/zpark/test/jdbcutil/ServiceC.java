package zpark.test.jdbcutil;

import org.springframework.transaction.annotation.Transactional;

public interface ServiceC {
	
	@Transactional
	public Integer c();

}
