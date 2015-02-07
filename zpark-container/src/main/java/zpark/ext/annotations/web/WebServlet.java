package zpark.ext.annotations.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebServlet {

    /**
     * The name of the servlet
     */
    String name() default "";

    /**
     * The URL patterns of the servlet
     */
    String urlPattern() default "";

    /**
     * The load-on-startup order of the servlet
     */
    int loadOnStartup() default -1;

    /**
     * Declares whether the filter supports asynchronous operation mode.
     * 
     * @see javax.servlet.ServletRequest#startAsync
     * @see javax.servlet.ServletRequest#startAsync(ServletRequest,
     *      ServletResponse)
     */
    boolean asyncSupported() default false;

}
