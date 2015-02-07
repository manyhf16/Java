package zpark.dao;

import java.io.Serializable;
import java.util.List;

import zpark.entity.SampleEntity;

public interface SampleDao {

    public String sample();

    public Serializable save(SampleEntity entity);

    public void update(SampleEntity entity);

    public void delete(int id);

    public SampleEntity findOne(int id);

    public List<SampleEntity> findAll();

}
