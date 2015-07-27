package zpark.ext.struts.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;
import org.apache.struts2.views.annotations.StrutsTag;

import com.opensymphony.xwork2.util.CompoundRoot;
import com.opensymphony.xwork2.util.ValueStack;

@StrutsTag(name = "grid", tldTagClass = "zpark.ext.struts.tag.GridTag", description = "Render an Table Grid", allowDynamicAttributes = true)
public class Grid extends ClosingUIBean {

    public static final String TEMPLATE = "grid";

    public static final String TEMPLATE_CLOSE = "grid-close";

    public Grid(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    public String getDefaultOpenTemplate() {
        return TEMPLATE;
    }

    @Override
    protected String getDefaultTemplate() {
        return TEMPLATE_CLOSE;
    }

    @Override
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();
        
    }

}
