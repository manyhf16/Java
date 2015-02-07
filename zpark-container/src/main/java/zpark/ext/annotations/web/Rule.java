package zpark.ext.annotations.web;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.regex.Pattern;

@Retention(RetentionPolicy.RUNTIME)
public @interface Rule {

    public interface CustomRule {
        public boolean check(String value, CustomRule callback, String options);
    }

    public enum RuleType implements CustomRule {
        NOT_NULL {
            public boolean check(String value, CustomRule callback, String options) {
                return value != null;
            }
        },
        NOT_EMPTY {
            public boolean check(String value, CustomRule callback, String options) {
                return value != null && value.trim().length() > 0;
            }
        },
        REGEX {
            @Override
            public boolean check(String value, CustomRule callback, String options) {
                if (value == null) {
                    return false;
                }
                return Pattern.compile(options).matcher(value).matches();
            }
        },
        IS_INTEGER {
            @Override
            public boolean check(String value, CustomRule callback, String options) {
                if (value == null) {
                    return false;
                }
                try {
                    Integer.valueOf(value);
                } catch (NumberFormatException e) {
                    return false;
                }
                return true;
            }
        },
        IS_DOUBLE {
            @Override
            public boolean check(String value, CustomRule callback, String options) {
                if (value == null) {
                    return false;
                }
                try {
                    Double.valueOf(value);
                } catch (NumberFormatException e) {
                    return false;
                }
                return true;
            }
        },
        CUSTOM {
            @Override
            public boolean check(String value, CustomRule rule, String options) {
                System.out.println("22222222222222222");
                return rule.check(value, rule, options);
            }
        };
    };

    public RuleType type();

    public String options() default "";

    public String message() default "";

    public Class<?> custom() default CustomRule.class;

}
