package zpark.service;

import java.util.List;

import zpark.entity.Classmate;
import zpark.entity.Location;

public interface ClassmateService {
	
public List<Classmate> findAll();
	
	public List<Classmate> findAllLost();
	
	public List<Classmate> findByGender(String gender);
	
	public List<Classmate> findByYear(int year);
	
	public List<Classmate> findByMonth(int month);
	
	public List<Classmate> findByYearAndMonth(int year, int month);
	
	public List<Classmate> findByConstellation(String constellation);
	
	public List<Classmate> findByLocation(int locationId);

	public List<Classmate> findByTag(String tag);
	
	public List<Classmate> findByName(String name);
	
	public Classmate findById(int id);
	
	public Classmate findByUsername(String username);
	
	public void update(Classmate classmate);
	
	public void updatePassword(int classmateId, String password);
	
	public List<Location> findAllLocations();
	
	public void deleteUnusedLocations();

}
