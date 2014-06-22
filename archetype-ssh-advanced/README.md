## Overview

Archetype-SSH-Advanced 是为了方便快速搭建SSH框架 (`struts2 spring3 hibernate3`)的脚手架项目，为了方便整合，还提供了一些常用扩展如：hibernate泛型DAO，struts的JSON转换拦截器等

## Table of contents

 - [Quick start](#quick-start)
 - [Features](#features)
 	- [Generic DAO Support](#generic-dao-support)

### Quick start

- [download and install git](http://git-scm.com/download/).
- [download and install maven](http://maven.apache.org/download.cgi).
- `$ git clone https://github.com/manyhf16/Java.git`.
- `$ cd /your git home/Java/archetype-ssh-advanced`.
- `$ mvn archetype:create-from-project`.
- `$ cd /your git home/Java/archetype-ssh-advanced/target/generated-sources/archetype`.
- `$ mvn install`.

Now you can find this Archetype in Maven's default local catelog.

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
	public Page<T> findPage(final int pageNo, final int pageSize, 
		final String hql, final String counthql, Object... objects);
	public Page<T> findPage(final int pageNo, final int pageSize, 
		final String hql, final String counthql, List<Object> objects);
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
