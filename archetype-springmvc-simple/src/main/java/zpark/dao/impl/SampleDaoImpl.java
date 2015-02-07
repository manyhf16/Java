package zpark.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import zpark.common.ext.HibernateTemplateExt;
import zpark.dao.SampleDao;
import zpark.entity.SampleEntity;

@Repository
public class SampleDaoImpl implements SampleDao {

    private static Logger logger = LoggerFactory.getLogger(SampleDaoImpl.class);

    @Autowired
    private HibernateTemplateExt templateExt;

    @Override
    public String sample() {
        logger.info("sample dao method...");
        return "Dao OK";
    }

    @Override
    public Serializable save(SampleEntity entity) {
        return templateExt.save(entity);
    }

    @Override
    public void update(SampleEntity entity) {
        templateExt.update(entity);
    }

    @Override
    public SampleEntity findOne(int id) {
        return templateExt.get(SampleEntity.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SampleEntity> findAll() {
        return (List<SampleEntity>) templateExt.find("from SampleEntity");
    }

    @Override
    public void delete(int id) {
        SampleEntity entity = templateExt.get(SampleEntity.class, id);
        if (entity != null) {
            templateExt.delete(entity);
        }
    }

}
