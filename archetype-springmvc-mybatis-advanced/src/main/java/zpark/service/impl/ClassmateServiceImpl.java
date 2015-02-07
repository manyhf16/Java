package zpark.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zpark.dao.ClassmateDao;
import zpark.entity.Classmate;
import zpark.entity.Location;
import zpark.service.ClassmateService;

@Service
public class ClassmateServiceImpl implements ClassmateService {

	@Autowired
	private ClassmateDao classmateDao;

	@Override
	public List<Classmate> findAll() {
		return classmateDao.findAll();
	}

	@Override
	public List<Classmate> findAllLost() {
		return classmateDao.findAllLost();
	}

	@Override
	public List<Classmate> findByGender(String gender) {
		return classmateDao.findByGender(gender);
	}

	@Override
	public List<Classmate> findByYear(int year) {
		return classmateDao.findByYear(year);
	}

	@Override
	public List<Classmate> findByMonth(int month) {
		return classmateDao.findByMonth(month);
	}

	@Override
	public List<Classmate> findByYearAndMonth(int year, int month) {
		return classmateDao.findByYearAndMonth(year, month);
	}

	@Override
	public List<Classmate> findByConstellation(String constellation) {
		return classmateDao.findByConstellation(constellation);
	}

	@Override
	public List<Classmate> findByLocation(int locationId) {
		return classmateDao.findByLocation(locationId);
	}

	@Override
	public List<Classmate> findByTag(String tag) {
		return classmateDao.findByTag(tag);
	}

	@Override
	public List<Classmate> findByName(String name) {
		return classmateDao.findByName(name);
	}

	@Override
	public Classmate findById(int id) {
		return classmateDao.findById(id);
	}

	@Override
	public Classmate findByUsername(String username) {
		return classmateDao.findByUsername(username);
	}

	@Override
	@Transactional
	public void update(Classmate classmate) {
		Location location = classmate.getLocation();
		if (location != null) { // 如果输入了位置
			Location dbLocation = classmateDao.findLocationByName(location.getName());
			if (dbLocation == null) { // 数据库没有此位置
				classmateDao.insertLocation(location);
			}
		}
		List<String> tags = classmate.getTags();
		if (tags != null) { // 先删除后添加
			classmateDao.deleteTags(classmate.getUsername());
			classmateDao.insertTags(classmate.getUsername(), tags);
		}
		classmateDao.update(classmate);
	}

	@Override
	@Transactional
	public void updatePassword(int classmateId, String password) {
		classmateDao.updatePassword(classmateId, password);
	}
	
	@Override
	public List<Location> findAllLocations() {
		return classmateDao.findAllLocations();
	}

	@Override
	@Transactional
	public void deleteUnusedLocations() {
		classmateDao.deleteUnusedLocations();
	}
	
}
