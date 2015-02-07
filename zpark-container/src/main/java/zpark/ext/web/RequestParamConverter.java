package zpark.ext.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zpark.ext.exception.ZparkConverterException;

public interface RequestParamConverter<T> {

    public T convert(String[] originalValue, Class<?> insideType) throws ZparkConverterException;

    public static RequestParamConverter<Integer> IntegerConverter = new RequestParamConverter<Integer>() {
        @Override
        public Integer convert(String[] originalValue, Class<?> insideType) throws ZparkConverterException {
            try {
                return Integer.parseInt(originalValue[0]);
            } catch (NumberFormatException e) {
                throw new ZparkConverterException("convert Integer or int error for value: [" + originalValue[0]
                        + "] is error");
            }
        }
    };

    public static RequestParamConverter<Long> LongConverter = new RequestParamConverter<Long>() {
        @Override
        public Long convert(String[] originalValue, Class<?> insideType) throws ZparkConverterException {
            try {
                return Long.parseLong(originalValue[0]);
            } catch (NumberFormatException e) {
                throw new ZparkConverterException("convert Long or long for value: [" + originalValue[0] + "] is error");
            }
        }
    };

    public static RequestParamConverter<List<?>> ListConverter = new RequestParamConverter<List<?>>() {
        @Override
        public List<?> convert(String[] originalValue, Class<?> insideType) throws ZparkConverterException {
            try {
                List<Object> list = new ArrayList<>();
                for (String value : originalValue) {
                    if (insideType == String.class) {
                        list.add(value);
                    } else {
                        Object av = converters.get(insideType).convert(new String[] { value }, null);
                        list.add(av);
                    }
                }
                return list;
            } catch (NumberFormatException e) {
                throw new ZparkConverterException("convert Long or long for value: [" + originalValue[0] + "] is error");
            }
        }
    };

    public static RequestParamConverter<Date> DateConverter = new RequestParamConverter<Date>() {
        @Override
        public Date convert(String[] originalValue, Class<?> insideType) throws ZparkConverterException {
            SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf_datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return sdf_datetime.parse(originalValue[0]);
            } catch (ParseException e1) {
                try {
                    return sdf_date.parse(originalValue[0]);
                } catch (ParseException e2) {
                    throw new ZparkConverterException(
                            "convert Date[yyyy-MM-dd] or DateTime[yyyy-MM-dd HH:mm:ss] for value: [" + originalValue[0]
                                    + "] is error");
                }
            }
        }
    };

    @SuppressWarnings("serial")
    public static final Map<Class<?>, RequestParamConverter<?>> converters = new HashMap<Class<?>, RequestParamConverter<?>>() {
        {
            put(Integer.class, IntegerConverter);
            put(int.class, IntegerConverter);
            put(Long.class, LongConverter);
            put(long.class, LongConverter);
            put(Date.class, DateConverter);
            put(List.class, ListConverter);
        }
    };

}
