package zpark.dao;

import java.util.List;

import zpark.entity.SampleEntity;
import zpark.ext.annotations.NamedPlaceholder;
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
	public Page<SampleEntity> sample();

	@Query(hql = "from SampleEntity where id > :id and name like :name")
	public Page<SampleEntity> find1(@PageNo int pageNo, @PageSize int pageSize, @NamedPlaceholder("id") int id,
			@NamedPlaceholder("name") String name);

	@Query(hql = "from SampleEntity where id > :id and name like :name")
	public Page<SampleEntity> find2(@PageNo int pageNo, @PageSize int pageSize, @NamedPlaceholder("id") int id,
			@NamedPlaceholder("name") String name, @OrderProperty String oc1, @OrderDirection String od1, @OrderProperty String oc2, @OrderDirection String od2);

	@Query(hql = "from SampleEntity")
	public Page<SampleEntity> find3(@PageNo int pageNo, @PageSize int pageSize, @OrderProperty String orderColumn,
			@OrderDirection String orderDirection, QueryBuilder queryBuilder);

	@Query(hql = "from SampleEntity where id in :list")
	public Page<SampleEntity> find4(int pageNo, int pageSize, @NamedPlaceholder("list") List<Integer> list);

}
