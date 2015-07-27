package zpark.test.mvc;


public class ResultStock {

    private String stockNo;

    public String getStockNo() {
        return stockNo;
    }

    public void setStockNo(String stockNo) {
        this.stockNo = stockNo;
    }

    public ResultStock(String stockNo) {
        super();
        this.stockNo = stockNo;
    }

}
