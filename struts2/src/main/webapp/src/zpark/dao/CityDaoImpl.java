package zpark.dao;

import java.util.List;
import java.util.Map;

import zpark.entity.City;
import zpark.ext.jdbc.MicroContrainer;

public class CityDaoImpl implements CityDao {

    public List<City> findCityList() {
        return MicroContrainer.queryList(City.class, "select id, name from city");
    }

    public Map<Long, String> findCityMap() {
        return MicroContrainer.queryIdMap(Long.class, String.class, "select id, name from city");
    }

}
