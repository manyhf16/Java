package zpark.test.mybatis;

import java.util.LinkedHashMap;

import org.junit.Test;

public class TestLinkedHashMap {
	
	@SuppressWarnings("serial")
	public static class CacheLinkedHashMap extends LinkedHashMap<String, Object>{
		
		public CacheLinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder) {
			super(initialCapacity, loadFactor, accessOrder);
		}
		private int size = 5;
		@Override
		protected boolean removeEldestEntry(java.util.Map.Entry<String, Object> eldest) {
			System.out.println(eldest);
			if(size() > size) {
				return true;
			}
			return false;
		}
	}
	
	@Test
	public void test01() {
		CacheLinkedHashMap cache = new CacheLinkedHashMap(10, 0.75f, true);
		cache.put("a", "aaa");
		System.out.println(cache);
		cache.put("b", "bbb");
		System.out.println(cache);
		
		cache.put("c", "ccc");
		System.out.println(cache);
		cache.put("d", "ddd");
		System.out.println(cache);
		cache.put("e", "eee");
		System.out.println(cache);
		cache.get("a");
		System.out.println(cache);
		cache.get("c");
		System.out.println(cache);
		cache.put("f", "fff");
		System.out.println(cache);
	}

}
