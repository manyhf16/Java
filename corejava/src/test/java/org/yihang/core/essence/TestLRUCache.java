package org.yihang.core.essence;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

public class TestLRUCache {

	@SuppressWarnings("serial")
	@Test
	public void test() {
		Map<String, Object> map = new LinkedHashMap<String, Object>(5, 0.75f, true) {
			private int limit = 5;
			@Override
			protected boolean removeEldestEntry(Entry<String, Object> eldest) {
				if (size() > limit) {
					return true;
				} else {
					return false;
				}
			}

		};

		map.put("1", "a");
		map.put("2", "a");
		map.put("3", "a");
		map.put("4", "a");
		map.put("5", "a");
		System.out.println(map);
		map.put("6", "a");
		System.out.println(map);
		map.get("4");
		System.out.println(map);
		map.put("7", "a");
		System.out.println(map);

		System.out.println(System.getProperty("java.io.tmpdir"));
	}

}
