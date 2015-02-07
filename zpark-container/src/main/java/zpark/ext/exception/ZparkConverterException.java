package zpark.ext.exception;

@SuppressWarnings("serial")
public class ZparkConverterException extends ZparkException {

    public ZparkConverterException() {
        super();
    }

    public ZparkConverterException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ZparkConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZparkConverterException(String message) {
        super(message);
    }

    public ZparkConverterException(Throwable cause) {
        super(cause);
    }

}
