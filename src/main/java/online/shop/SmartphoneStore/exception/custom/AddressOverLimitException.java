package online.shop.SmartphoneStore.exception.custom;

public class AddressOverLimitException extends Exception{
    public AddressOverLimitException() {
    }

    public AddressOverLimitException(String message) {
        super(message);
    }

    public AddressOverLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddressOverLimitException(Throwable cause) {
        super(cause);
    }

    public AddressOverLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
