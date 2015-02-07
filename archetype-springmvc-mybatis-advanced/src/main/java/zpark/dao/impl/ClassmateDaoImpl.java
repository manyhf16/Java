package zpark.dao.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

import zpark.dao.ClassmateDao;
import zpark.entity.Classmate;
import zpark.entity.Location;

@Repository
public class ClassmateDaoImpl implements ClassmateDao {

	@Autowired
	@Qualifier("readSqlSession")
	private SqlSession readSqlSession;
	
	@Autowired
	@Qualifier("writeSqlSession")
	private SqlSession writeSqlSession;

	@Override
	public List<Classmate> findAll() {
		return readSqlSession.selectList("findAll");
	}

	@Override
	public List<Classmate> findByGender(String gender) {
		return readSqlSession.selectList("findByGender", gender);
	}

	@Override
	public List<Classmate> findAllLost() {
		return readSqlSession.selectList("findAllLost");
	}

	@Override
	public List<Classmate> findByYear(int year) {
		return readSqlSession.selectList("findByYear", year);
	}

	@Override
	public List<Classmate> findByMonth(int month) {
		return readSqlSession.selectList("findByMonth", month);
	}

	@Override
	public List<Classmate> findByYearAndMonth(int year, int month) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("year", year);
		map.put("month", month);
		return readSqlSession.selectList("findByYearAndMonth", map);
	}

	@Override
	public List<Classmate> findByConstellation(String constellation) {
		return readSqlSession.selectList("findByConstellation", constellation);
	}

	@Override
	public List<Classmate> findByLocation(int locationId) {
		return readSqlSession.selectList("findByLocation", locationId);
	}

	@Override
	public List<Classmate> findByTag(String tag) {
		return readSqlSession.selectList("findByTag", tag);
	}
	
	@Override
	public List<Classmate> findByName(String name) {
		return readSqlSession.selectList("findByName", name + "%");
	}

	@Override
	public Classmate findById(int id) {
		return writeSqlSession.selectOne("findById", id);
	}

	@Override
	public Classmate findByUsername(String username) {
		return writeSqlSession.selectOne("findByUsername", username);
	}

	@Override
	public void update(Classmate classmate) {
		writeSqlSession.update("update", classmate);		
	}

	@Override
	public void updatePassword(int classmateId, String password) {
		byte[] bytes = null;
		try {
			bytes = password.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("classmateId", classmateId);
		System.out.println(DigestUtils.md5DigestAsHex(bytes));
		map.put("password", DigestUtils.md5DigestAsHex(bytes));
		writeSqlSession.update("updatePassword", map);
	}

	@Override
	public void deleteTags(String username) {
		writeSqlSession.delete("deleteTags", username);
	}

	@Override
	public void insertTags(String username, List<String> tags) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		map.put("tags", tags);
		writeSqlSession.delete("insertTags", map);
	}

	@Override
	public Location findLocationById(int locationId) {
		return writeSqlSession.selectOne("findLocationById", locationId);
	}
	
	@Override
	public Location findLocationByName(String locationName) {
		return writeSqlSession.selectOne("findLocationByName", locationName);
	}
	
	@Override
	public List<Location> findAllLocations() {
		return writeSqlSession.selectList("findAllLocations");
	}

	@Override
	public void insertLocation(Location location) {
		writeSqlSession.insert("insertLocation", location);		
	}

	@Override
	public void deleteUnusedLocations() {
		writeSqlSession.delete("deleteUnusedLocations");
	}

	@Override
	public String findConstellation(Date date) {
		return readSqlSession.selectOne("findConstellation", date);
	}

}
