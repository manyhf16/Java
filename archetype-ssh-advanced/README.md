## Overview

Archetype-SSH-Advanced 是为了方便快速搭建SSH框架 (`struts2 spring3 hibernate3`)的脚手架项目，另外还提供了一些常用扩展如：hibernate泛型DAO，struts的JSON转换拦截器等

## Table of contents

 - [Quick start](#quick-start)
 - [Features](#features)
 	- [Generic DAO Support](#generic-dao-support)
 	- [Json Interceptor](#json-interceptor)

### Quick start

- [download and install git](http://git-scm.com/download/).
- [download and install maven](http://maven.apache.org/download.cgi).
- `$ git clone https://github.com/manyhf16/Java.git`.
- `$ cd /your git home/Java/archetype-ssh-advanced`.
- `$ mvn archetype:create-from-project`.
- `$ cd /your git home/Java/archetype-ssh-advanced/target/generated-sources/archetype`.
- `$ mvn install`.

Now you can create new maven project use this archetype.
In eclipse just follow: `File->New->Maven Project->Next->Choose Catelog->Default Local`, and you'll find this archetype.

### Features

#### Generic DAO Support
You can build your DAO interface like this:
```java
public interface ProductDao extends GenericDao<Product, Integer> {

}
```
No more concrete class implements is needed! Cause all necessary method is define in its super interface:
```java
public interface GenericDao<T, ID extends Serializable> {
	public ID save(T entity);
	public void update(T entity);
	public void delete(ID id);
	public T findOne(ID id);
	public List<T> findAll();
	public Page<T> findPage(final int pageNo, final int pageSize, final String hql, final String counthql, 
			Object... objects);
	public Page<T> findPage(final int pageNo, final int pageSize, final String hql, final String counthql, 
			List<Object> objects);
	public T findOne(final String hql, final Object... objects);	
	public <W> W findOne(Class<W> c, final String hql, final Object... objects) ;
}
```
Aslo, Use Dependency Inject to your Service bean:
```java
@Service @Transactional
public class ProductServiceImpl implements ProductService {
	private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	@Autowired
	private ProductDao productDao;
	...
}
```
You can use @Query, @PageNo, @PageSize, @OrderProperty, @OrderDirection annotations to improve your DAO:
```java
public interface SampleDao extends GenericDao<SampleEntity, Integer> {

	@Query(hql = "from SampleEntity where id > ? and name like ?")
	public Page<SampleEntity> find1(@PageNo int pageNo, @PageSize int pageSize, int id, String name);

	@Query(hql = "from SampleEntity where id > ? and name like ?")
	public Page<SampleEntity> find2(@PageNo int pageNo, @PageSize int pageSize, int id, String name, 
			@OrderProperty String orderColumn, @OrderDirection String orderDirection);

	@Query(hql = "from SampleEntity")
	public Page<SampleEntity> find3(@PageNo int pageNo, @PageSize int pageSize, @OrderProperty String orderColumn,
			@OrderDirection String orderDirection, QueryBuilder queryBuilder);
}
```
QueryBuilder is a callback interface to generate the dynamic HQL statement:
```java
public final class SampleQueryBuilder implements QueryBuilder {
	private final Integer id;
	private final String name;
	public SampleQueryBuilder(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	@Override
	public void build(StringBuilder sb, List<Object> params) {
		if (id != null) {
			sb.append(" and id > ?");
			params.add(id);
		}
		if (name != null) {
			sb.append(" and name like ?");
			params.add(name);
		}
	}
}
```
#### Json Interceptor
I provide an interceptor to render json view, auto result type detected, add follow configuration in your struts.xml:
```xml
<package name="default" extends="struts-default">
	...
	<interceptors>
		<interceptor name="jsonResult" class="zpark.ext.struts.JsonResultInterceptor"/>
	</interceptors>
	<action name="sample" class="sampleAction" method="execute">
		<interceptor-ref name="defaultStack"/>
		<interceptor-ref name="jsonResult"/>
		<result name="success">/WEB-INF/view/sampleView.jsp</result>
		<result name="input">/index.jsp</result> 
	</action>
</package>
```
while `X-Requested-With=XMLHttpRequest` header in http request, the result will be render to json string, or, result is forward to its original definition.
