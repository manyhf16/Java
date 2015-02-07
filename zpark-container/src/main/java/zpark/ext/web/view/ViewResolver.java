package zpark.ext.web.view;

import javax.servlet.http.HttpServletResponse;

public interface ViewResolver {

    public abstract void resolve(View view, HttpServletResponse resp) throws Exception;

}
