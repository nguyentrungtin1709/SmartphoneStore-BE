package online.shop.SmartphoneStore.service.Interface;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.Enum.OrderStatus;
import online.shop.SmartphoneStore.entity.Order;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import org.springframework.data.domain.Page;

public interface OrderService {

    Order createOrder(Order order, Account account);

    Page<Order> readAllOrdersByAccount(Account account, Integer page);

    Order readOrderByIdAndAccount(Account account, Long orderId) throws DataNotFoundException;

    Order cancelOrder(Account account, Long orderId) throws DataNotFoundException;

    Page<Order> readAllOrders(Integer page);

    Page<Order> readOrdersByStatus(OrderStatus status, Integer page);

    Order readOrderById(Long orderId) throws DataNotFoundException;

    void deleteOrderById(Long orderId) throws DataNotFoundException;

    Order updateStatusOfOrderById(Long orderId, OrderStatus status) throws DataNotFoundException;
}
