package zpark.ext.struts.tag;

import java.util.List;

public interface Pager {

    public abstract List<?> getList();

    public abstract List<Column> getColumns();

    public abstract int getPageLeft();

    public abstract int getPageMax();

    public abstract int getEnd();

    public abstract int getTotal();

    public abstract int getBegin();

    public abstract int getPageSize();

    public abstract int getPageNo();

    public abstract int getCount();

}
