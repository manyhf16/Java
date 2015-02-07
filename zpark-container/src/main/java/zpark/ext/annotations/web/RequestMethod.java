package zpark.ext.annotations.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMethod {

    public static final String PRIVATE = "private";

    public static final String NO_CACHE = "no-cache";

    public static final String MAX_AGE = "max-age=";

    public static enum METHOD {
        GET, POST, HEAD, PUT, DELETE, OPTIONS, TRACE;
    }

    public String path() default "";

    public METHOD method() default METHOD.GET;

    public boolean cacheable() default false;

    public boolean evictcache() default false;

    public boolean client_cache() default false;

    public String cache_control() default NO_CACHE;

}
