package com.xx.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 简单工厂类，根据配置文件中的定义创建各个接口的实现类的实例
 * 
 * @author yihang
 *
 */
public class Factory {
	// 用来读取配置文件
	private static Properties props;

	// 用来保存各个实现类的单实例
	private static Map<Class<?>, Object> map = new HashMap<>();

	static {
		props = new Properties();

		// 读取配置文件
		try (InputStream is = Factory.class.getResourceAsStream("/factory.properties")) {
			props.load(is);

			// 遍历Properties: key(接口类名) value(实现类名)
			Set<Object> keys = props.keySet();
			for (Object k : keys) {
				String key = k.toString();
				String value = props.getProperty(key);

				// 将接口的Class和实现类的实例作为键值放入map集合
				map.put(Class.forName(key), Class.forName(value).newInstance());
			}
		} catch (Throwable e) {
			throw new Error(e);
		}
	}

	/**
	 * 根据接口的类型返回它在配置文件中对应的实现类
	 * 
	 * @param c
	 * @return
	 */
	public static <T> T getBean(Class<T> c) {
		return c.cast(map.get(c));
	}

}
