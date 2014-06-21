package zpark.dao;

import zpark.entity.SampleEntity;
import zpark.ext.annotations.OrderDirection;
import zpark.ext.annotations.OrderProperty;
import zpark.ext.annotations.PageNo;
import zpark.ext.annotations.PageSize;
import zpark.ext.annotations.Query;
import zpark.ext.hibernate.GenericDao;
import zpark.ext.query.Page;
import zpark.ext.query.QueryBuilder;

public interface SampleDao extends GenericDao<SampleEntity, Integer> {

	@Query(hql = "from SampleEntity")
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
