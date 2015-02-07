package zpark.ext.web.view;

import java.util.HashMap;
import java.util.Map;

import zpark.ext.exception.ZparkException;

public abstract class ViewResolvers {
    
    @SuppressWarnings("serial")
    private static final Map<Class<? extends View>, ViewResolver> map = new HashMap<Class<? extends View>, ViewResolver>(){{
        put(FreeMarkerView.class, FreeMarkerViewResolver.getInstance());
    }};
    
    public static ViewResolver findViewResolver(View view) {
        ViewResolver viewResolver = map.get(view.getClass());
        if(viewResolver == null) {
            throw new ZparkException("no ViewResolver for view: " + view);
        }
        return viewResolver;
    }

}
