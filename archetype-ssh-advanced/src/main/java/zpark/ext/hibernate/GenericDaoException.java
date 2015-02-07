package zpark.ext.hibernate;

@SuppressWarnings("serial")
public class GenericDaoException extends RuntimeException {

	public GenericDaoException() {
		super();
	}

	public GenericDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public GenericDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public GenericDaoException(String message) {
		super(message);
	}

	public GenericDaoException(Throwable cause) {
		super(cause);
	}

}
