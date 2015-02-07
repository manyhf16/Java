package zpark.ext.exception;

@SuppressWarnings("serial")
public class ZparkException extends RuntimeException {

    public ZparkException() {
        super();
    }

    public ZparkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ZparkException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZparkException(String message) {
        super(message);
    }

    public ZparkException(Throwable cause) {
        super(cause);
    }

}
