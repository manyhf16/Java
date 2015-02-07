package zpark.ext.web.view;

public class FreeMarkerView implements View {

    private String name;
    private Object model;
    private String contentType = HTML;
    private String encoding = DEFAULT_ENCODING;

    public FreeMarkerView(String name, Object model) {
        super();
        this.name = name;
        this.model = model;
    }

    public FreeMarkerView(String name, Object model, String contentType, String encoding) {
        super();
        this.name = name;
        this.model = model;
        this.contentType = contentType;
        this.encoding = encoding;
    }

    public String getName() {
        return name;
    }

    public Object getModel() {
        return model;
    }

    public String getContentType() {
        return contentType;
    }

    public String getEncoding() {
        return encoding;
    }

}
