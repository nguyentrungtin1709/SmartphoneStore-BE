package online.shop.SmartphoneStore.service.Interface;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.Order;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import org.springframework.data.domain.Page;

public interface OrderService {

    Order createOrder(Order order, Account account);

    Page<Order> readAllOrders(Account account, Integer page);

    Order readOrderById(Account account, Long orderId) throws DataNotFoundException;

    Order cancelOrder(Account account, Long orderId) throws DataNotFoundException;

}
