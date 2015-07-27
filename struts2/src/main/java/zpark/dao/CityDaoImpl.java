package zpark.dao;

import java.util.List;
import java.util.Map;

import zpark.entity.City;
import zpark.ext.jdbc.JdbcUtil;

public class CityDaoImpl implements CityDao {

    public List<City> findCityList() {
        return JdbcUtil.queryList(City.class, "select id, name from STRUTS2_CITY");
    }

    public Map<Long, String> findCityMap() {
        return JdbcUtil.queryIdMap(Long.class, String.class, "select id, name from STRUTS2_CITY");
    }

}
