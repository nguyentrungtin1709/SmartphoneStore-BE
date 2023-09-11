package online.shop.SmartphoneStore.exception.custom;

import java.util.HashMap;
import java.util.Map;

public class UniqueConstraintException extends Exception{

    private final Map<String, String> columns;

    public UniqueConstraintException() {
        this.columns = new HashMap<>();
    }

    public UniqueConstraintException(Map<String, String> columns){
        this.columns = columns;
    }

    public UniqueConstraintException(String message) {
        super(message);
        this.columns = new HashMap<>();
    }

    public UniqueConstraintException(String message, Throwable cause) {
        super(message, cause);
        this.columns = new HashMap<>();
    }

    public UniqueConstraintException(Throwable cause) {
        super(cause);
        this.columns = new HashMap<>();
    }

    public UniqueConstraintException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.columns = new HashMap<>();
    }

    public Map<String, String> getColumns() {
        return columns;
    }
}
