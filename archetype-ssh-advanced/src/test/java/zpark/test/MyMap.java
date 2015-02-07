package zpark.test;

import java.util.HashMap;

@SuppressWarnings("serial")
public class MyMap extends HashMap<String, Object>{
	
	{
		put("name", "zhang");
		put("age", 18);
	}

	public String exists(Object key, String sql) {
		Object value = get(key);
		if (value == null || value.equals("")) {
			return "";
		} else {
			return sql;
		}
	}
	
//	public String getName() {
//		return "ok";
//	}

}
