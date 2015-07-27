package zpark.ext.struts.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings("serial")
public class ColumnTag extends AbstractUITag{
    
    private String text;
    
    private String index;

    public void setText(String text) {
        this.text = text;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Column(stack, req, res);
    }

    @Override
    protected void populateParams() {
        Column column = (Column) getComponent();
        column.setIndex(index);
        column.setText(text);
    }
}
