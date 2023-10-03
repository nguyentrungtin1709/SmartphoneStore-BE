package online.shop.SmartphoneStore.service.Interface;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.Order;

public interface OrderService {

    public Order createOrder(Order order, Account account);

}
