package online.shop.SmartphoneStore.service;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.Enum.OrderStatus;
import online.shop.SmartphoneStore.entity.Order;
import online.shop.SmartphoneStore.entity.OrderDetails;
import online.shop.SmartphoneStore.entity.Smartphone;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.exception.custom.QuantityInStockIsEmptyException;
import online.shop.SmartphoneStore.repository.OrderDetailsRepository;
import online.shop.SmartphoneStore.repository.OrderRepository;
import online.shop.SmartphoneStore.repository.SmartphoneRepository;
import online.shop.SmartphoneStore.service.Interface.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImplement implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderDetailsRepository orderDetailsRepository;

    private final SmartphoneRepository smartphoneRepository;

    public OrderServiceImplement(
            OrderRepository orderRepository,
            OrderDetailsRepository orderDetailsRepository,
            SmartphoneRepository smartphoneRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.smartphoneRepository = smartphoneRepository;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Order createOrder(Order order, Account account) {
        order.setAccount(account);
        order.setStatus(OrderStatus.PENDING);
        List<OrderDetails> orderItemList = order
                .getOrderItemList()
                .stream()
                .map(item -> {
                    Smartphone smartphone = null;
                    try {
                        smartphone = smartphoneRepository
                                .findById(item.getSmartphone().getId())
                                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy sản phẩm"));
                    } catch (DataNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    item.setSmartphone(smartphone);
                    item.setPrice(smartphone.getPrice());
                    return item;
                })
                .collect(Collectors.toCollection(ArrayList::new));

        List<OrderDetails> errors = orderItemList
                .stream()
                .filter(item -> item.getSmartphone().getQuantityInStock() < item.getQuantity())
                .collect(Collectors.toCollection(ArrayList::new));
        if (!errors.isEmpty()){
            throw new RuntimeException(new QuantityInStockIsEmptyException(
               errors
                   .stream()
                   .map(OrderDetails::getSmartphone)
                   .collect(Collectors.toList())
            ));
        }
        orderItemList = orderItemList
                .stream()
                .map(item -> {
                    Integer quantity = item.getSmartphone().getQuantityInStock() - item.getQuantity();
                    Smartphone smartphone = item.getSmartphone();
                    smartphone.setQuantityInStock(quantity);
                    smartphone = smartphoneRepository.save(smartphone);
                    item.setSmartphone(smartphone);
                    return item;
                }).collect(Collectors.toList());
        Integer total = orderItemList
                .stream()
                .mapToInt(OrderDetails::getTotal)
                .sum();
        order.setOrderItemList(null);
        order.setTotal(total);
        Order orderResponse = orderRepository.save(order);
        orderItemList = orderItemList
                .stream()
                .map(item -> {
                    item.setOrder(Order
                            .builder()
                            .id(orderResponse.getId())
                            .build()
                    );
                    return item;
                })
                .collect(Collectors.toCollection(ArrayList::new));
        orderDetailsRepository.saveAll(orderItemList);
        order.setOrderItemList(orderItemList);
        return orderResponse;
    }

}
