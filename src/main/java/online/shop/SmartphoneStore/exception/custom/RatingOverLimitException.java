package online.shop.SmartphoneStore.exception.custom;

public class RatingOverLimitException extends Exception{
    public RatingOverLimitException() {
    }

    public RatingOverLimitException(String message) {
        super(message);
    }

    public RatingOverLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public RatingOverLimitException(Throwable cause) {
        super(cause);
    }

    public RatingOverLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
