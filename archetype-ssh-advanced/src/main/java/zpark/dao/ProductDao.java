package zpark.dao;

import java.util.List;

import zpark.entity.Product;
import zpark.ext.annotations.NamedPlaceholder;
import zpark.ext.annotations.Query;
import zpark.ext.hibernate.GenericDao;

public interface ProductDao extends GenericDao<Product, Integer> {

	// @Query(hql="from Product where 1=1 #{exists('and name = :name')} #{exists('and price between :begin and :end')}")
	@Query(hql = "from Product where 1=1 #{and name = :name} #{and price in :all}")
	public List<Product> findByName(
			@NamedPlaceholder("name") String name, 
			@NamedPlaceholder("all") List<Integer> all
	);

}
