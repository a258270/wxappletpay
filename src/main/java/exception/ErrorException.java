package exception;

/**
 * description:
 *
 * @author: zmj
 * @create: 2018/1/10
 */
public class ErrorException extends Exception {

    public ErrorException(String message) {
        super(message);
    }

    public ErrorException() {
        super();
    }

    public ErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorException(Throwable cause) {
        super(cause);
    }

    protected ErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
