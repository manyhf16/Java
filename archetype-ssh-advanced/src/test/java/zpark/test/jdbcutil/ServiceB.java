package zpark.test.jdbcutil;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ServiceB {
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Integer b();

}
