package zpark.test;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zpark.entity.City;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public class TestGson {
	
//	@Autowired
//	private SessionFactory sessionFactory;
	
	@Test
	public void test1() {
//		Session session = sessionFactory.openSession();
//		
//		session.createQuery("from City where id in (:ids)").setParameterList("ids", new Integer[]{1,2,3}).list();
//		List<City> list = session.createQuery("select id, name from City where (1,id) in ((1,1),(1,2),(1,3))").list();
	    
	    City c = new City();
	    c.setId(1);
	    c.setName("北京");
//		City c = (City) session.load(City.class, 1);
		
		GsonBuilder gsonBuilder=new GsonBuilder();
//		gsonBuilder.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		gsonBuilder.registerTypeHierarchyAdapter(City.class, new com.google.gson.JsonSerializer<City>() {

			@Override
			public JsonElement serialize(City src, Type typeOfSrc, JsonSerializationContext context) {
			    JsonObject e = new JsonObject();
			    e.addProperty("id", src.getId());
			    e.addProperty("name", src.getName());
				return e;
			}
		});
		System.out.println(gsonBuilder.create().toJson(c));
	}

}
