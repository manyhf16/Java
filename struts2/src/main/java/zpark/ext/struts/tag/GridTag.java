package zpark.ext.struts.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings("serial")
public class GridTag extends AbstractUITag {

    @Override
    public Component getBean(ValueStack vs, HttpServletRequest request, HttpServletResponse response) {
        return new Grid(vs, request, response);
    }

}
