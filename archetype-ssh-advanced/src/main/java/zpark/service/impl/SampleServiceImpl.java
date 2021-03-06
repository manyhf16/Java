package zpark.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zpark.dao.SampleDao;
import zpark.entity.SampleEntity;
import zpark.service.SampleService;

@Service
@Transactional
public class SampleServiceImpl implements SampleService {

	private static Logger logger = LoggerFactory.getLogger(SampleServiceImpl.class);

	@Autowired
	private SampleDao sampleDao;

	@Override
	public List<SampleEntity> findAll() {
		logger.info("sample service method...");
		return null;
//		return sampleDao.findAll();
	}

    @Override
    public void save(SampleEntity entity) {
        sampleDao.save(entity);
    }

}
