package zpark.dao;

import java.util.List;
import java.util.Map;

import zpark.entity.City;

public interface CityDao {

    /**
     * 查询所有城市
     * 
     * @return
     */
    public List<City> findCityList();

    /**
     * 查询所有城市
     * 
     * @return
     */
    public Map<Long, String> findCityMap();

}
