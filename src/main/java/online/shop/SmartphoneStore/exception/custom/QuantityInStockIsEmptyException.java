package online.shop.SmartphoneStore.exception.custom;

import online.shop.SmartphoneStore.entity.Smartphone;

import java.util.ArrayList;
import java.util.List;

public class QuantityInStockIsEmptyException extends Exception{

    private List<Smartphone> smartphones = new ArrayList<>();

    public QuantityInStockIsEmptyException() {
    }

    public QuantityInStockIsEmptyException(String message) {
        super(message);
    }

    public QuantityInStockIsEmptyException(List<Smartphone> smartphones){
        this.smartphones = smartphones;
    }

    public QuantityInStockIsEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuantityInStockIsEmptyException(Throwable cause) {
        super(cause);
    }

    public QuantityInStockIsEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public List<Smartphone> getSmartphone(){
        return smartphones;
    }
}
