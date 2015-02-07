package zpark.ext.annotations.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {

    public static final String[] EMPTY_ARRAY = new String[0];

    public String name();

    public String[] defaultValue() default {};

    public Rule[] rules() default {};

}
