package zpark.test.mvc;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Response {

    private ResultStockList resultStockList = new ResultStockList();
    private PayStatusList payStatusList = new PayStatusList();

    public ResultStockList getResultStockList() {
        return resultStockList;
    }

    public void setResultStockList(ResultStockList resultStockList) {
        this.resultStockList = resultStockList;
    }

    public PayStatusList getPayStatusList() {
        return payStatusList;
    }

    public void setPayStatusList(PayStatusList payStatusList) {
        this.payStatusList = payStatusList;
    }

}
