package zpark.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zpark.dao.SampleDao;
import zpark.service.SampleService;

@Service
@Transactional
public class SampleServiceImpl implements SampleService {

	private static Logger logger = LoggerFactory.getLogger(SampleServiceImpl.class);

	@Autowired
	private SampleDao sampleDao;

	public String sample() {
		logger.info("sample service method...");
		sampleDao.findAll();
		return "Service OK";
	}

}
