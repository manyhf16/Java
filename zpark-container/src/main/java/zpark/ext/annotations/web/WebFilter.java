package zpark.ext.annotations.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.servlet.DispatcherType;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebFilter {

    /**
     * The names of the servlets to which the filter applies.
     */
    String[] servletNames() default {};

    /**
     * The URL patterns to which the filter applies
     */
    String[] urlPatterns() default {};

    /**
     * The dispatcher types to which the filter applies
     */
    DispatcherType[] dispatcherTypes() default { DispatcherType.REQUEST };

    /**
     * Declares whether the filter supports asynchronous operation mode.
     * 
     * @see javax.servlet.ServletRequest#startAsync
     * @see javax.servlet.ServletRequest#startAsync(ServletRequest,
     *      ServletResponse)
     */
    boolean asyncSupported() default false;

}
