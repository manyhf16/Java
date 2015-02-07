package zpark.test.jdbcutil;

import org.springframework.transaction.annotation.Transactional;

public interface ServiceA {
	
	@Transactional
	public Integer a();

}
