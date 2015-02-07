package zpark.test.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;

import zpark.entity.City;

public class CityDaoImpl implements CityDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void save(City city) {
		jdbcTemplate.update("insert into city(id, name) values(?, ?)", city.getId(), city.getName());
		jdbcTemplate.update("insert into city(name) values(?)", "错误");
	}

}
