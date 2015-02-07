package zpark.ext.web.view;

import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;

public class FreeMarkerViewResolver implements ViewResolver {

    private Configuration configuration;

    private static FreeMarkerViewResolver me = new FreeMarkerViewResolver();

    public FreeMarkerViewResolver() {
        configuration = new Configuration();
        configuration.setDefaultEncoding(View.DEFAULT_ENCODING);
        configuration.setClassForTemplateLoading(FreeMarkerViewResolver.class, "/zpark/view");
    }

    @Override
    public void resolve(View view, HttpServletResponse resp) throws Exception {
        FreeMarkerView v = (FreeMarkerView) view;
        resp.setCharacterEncoding(v.getEncoding());
        resp.setContentType(v.getContentType());
        configuration.getTemplate(v.getName()).process(v.getModel(), resp.getWriter());
    }

    public static FreeMarkerViewResolver getInstance() {
        return me;
    }

}
