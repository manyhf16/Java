package zpark.service;

import java.util.List;

import zpark.entity.SampleEntity;

public interface SampleService {

	public List<SampleEntity> findAll();
	
	public void save(SampleEntity entity);

}
