package zpark.dao;

import zpark.common.dao.GenericDao;
import zpark.common.dao.Page;
import zpark.common.dao.QueryBuilder;
import zpark.common.dao.annotations.OrderDirection;
import zpark.common.dao.annotations.OrderProperty;
import zpark.common.dao.annotations.PageNo;
import zpark.common.dao.annotations.PageSize;
import zpark.common.dao.annotations.Query;
import zpark.entity.SampleEntity;

public interface SampleDao extends GenericDao<SampleEntity, Integer> {

	public String sample();

	@Query(hql = "from SampleEntity where id > ? and name like ?")
	public Page<SampleEntity> find1(@PageNo int pageNo, @PageSize int pageSize, int id, String name);

	@Query(hql = "from SampleEntity where id > ? and name like ?")
	public Page<SampleEntity> find2(@PageNo int pageNo, @PageSize int pageSize, int id, String name, @OrderProperty String orderColumn,
			@OrderDirection String orderDirection);
	
	@Query(hql = "from SampleEntity")
	public Page<SampleEntity> find3(@PageNo int pageNo, @PageSize int pageSize, @OrderProperty String orderColumn,
			@OrderDirection String orderDirection, QueryBuilder queryBuilder);

}
