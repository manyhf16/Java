package zpark.ext.struts.tag;

import java.util.ArrayList;
import java.util.List;

public abstract class PagerAction implements Pager {
    private int pageNo = 1;
    private int pageSize = 10;
    private int begin;
    private int total;
    private int end;
    private int pageMax = 10;
    private int pageLeft = 5;
    private int count;
    private List<Column> columns = new ArrayList<Column>();
    private List<?> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        if (count % pageSize == 0) {
            total = count / pageSize;
        } else {
            total = count / pageSize + 1;
        }
        calulateBeginEnd();
    }

    private void calulateBeginEnd() {
        int right = pageMax - pageLeft - 1;
        if (total <= pageMax) {
            begin = 1;
            end = total;
        } else {
            if (pageNo <= pageLeft + 1) {
                begin = 1;
                end = pageMax;
            } else {
                if (total - pageNo <= right) {
                    begin = total - (pageMax - 1);
                    end = total;
                } else {
                    begin = pageNo - pageLeft;
                    end = pageNo + right;
                }
            }
        }
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getBegin() {
        return begin;
    }

    public int getTotal() {
        return total;
    }

    public int getEnd() {
        return end;
    }

    public int getPageMax() {
        return pageMax;
    }

    public void setPageMax(int pageMax) {
        this.pageMax = pageMax;
    }

    public int getPageLeft() {
        return pageLeft;
    }

    public void setPageLeft(int pageLeft) {
        this.pageLeft = pageLeft;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<?> getList() {
        return list;
    }

    public void createPager(List<?> list, int count) {
        this.list = list;
        setCount(count);
    }

}
