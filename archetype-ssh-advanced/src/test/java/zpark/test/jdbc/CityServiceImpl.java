package zpark.test.jdbc;

import zpark.entity.City;

public class CityServiceImpl implements CityService {
	
	private CityDao cityDao;
	
	public void setCityDao(CityDao cityDao) {
		this.cityDao = cityDao;
	}

	@Override
	@Authenticate("1")
	public void save(City city) {
		cityDao.save(city);
	}

}
