package zpark.ext.web;

import java.util.Arrays;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.constructs.blocking.BlockingCache;

import org.junit.Test;

public class TestCache {

	@Test
	public void test01() {

		Cache cache = new Cache(new CacheConfiguration().name("test").maxElementsInMemory(10).overflowToDisk(false).timeToIdleSeconds(60));

		CacheManager manager = CacheManager.getInstance();
		manager.addCacheIfAbsent(cache);
		manager.getCache("test").put(new Element(1, "ok"));

		Element e = manager.getCache("test").get(1);
		System.out.println(e.getObjectValue());
	}
	
	@Test
	public void test02() {
		CacheManager manager = CacheManager.getInstance();
		BlockingCache blockingCache;
		Cache cache = new Cache(new CacheConfiguration().name("test").maxElementsInMemory(10).overflowToDisk(false)
				.timeToIdleSeconds(60));
		blockingCache = new BlockingCache(cache);
		Object o = blockingCache;
		System.out.println(o instanceof BlockingCache);
		blockingCache.setTimeoutMillis(10000);
		manager.addCacheIfAbsent(cache);
		System.out.println("=========================>"+manager.getCache("test"));
		System.out.println("=========================>"+manager.getEhcache("test"));
//		manager.replaceCacheWithDecoratedCache(cache, blockingCache);
//		System.out.println(Arrays.toString(manager.getCacheNames()));
//		manager.getCache("test");
//		
//		System.out.println("=========================>"+manager.getEhcache("test"));
	}

}
