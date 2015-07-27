package zpark.ext.struts.tag;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.opensymphony.xwork2.util.CompoundRoot;
import com.opensymphony.xwork2.util.ValueStack;

@StrutsTag(name = "column", tldTagClass = "zpark.ext.struts.tag.ColumnTag", description = "column tag")
public class Column extends Component {

    private String text;
    private String index;

    public String getText() {
        return text;
    }

    @StrutsTagAttribute(description = "title for the grid's column", type = "String", required = true)
    public void setText(String text) {
        this.text = text;
    }

    public String getIndex() {
        return index;
    }

    @StrutsTagAttribute(description = "field name for the grid's column", type = "String", required = true)
    public void setIndex(String index) {
        this.index = index;
    }

    public Column(String text, String index) {
        super(null);
        this.text = text;
        this.index = index;
    }

    public Column() {
        super(null);
    }

    public Column(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        super(stack);
    }

    public Object translate(Object obj) {
        return obj;
    }

    @Override
    public boolean start(Writer writer) {
        CompoundRoot root = getStack().getRoot();
        boolean found = false;
        for (Object o : root) {
            if (o instanceof Pager) {
                found = true;
                Pager pager = (Pager) o;
                pager.getColumns().add(this);
                break;
            }
        }
        if (!found) {
            throw new RuntimeException("您的action没有实现Pager接口");
        }
        return super.start(writer);
    }

}
