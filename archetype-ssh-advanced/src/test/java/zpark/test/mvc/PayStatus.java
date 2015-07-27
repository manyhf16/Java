package zpark.test.mvc;

public class PayStatus {

    private String stt;
    private String status;
    private String statusText;

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public PayStatus(String stt, String status, String statusText) {
        super();
        this.stt = stt;
        this.status = status;
        this.statusText = statusText;
    }

}
