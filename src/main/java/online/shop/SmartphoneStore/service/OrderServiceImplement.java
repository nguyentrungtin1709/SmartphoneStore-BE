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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public Page<Order> readAllOrdersByAccount(Account account, Integer page, OrderStatus status) {
        if (status != null) {
            return orderRepository.findOrdersByAccount_IdAndStatusOrderByCreatedAtDesc(
                    account.getId(),
                    status,
                    PageRequest.of(page, 4)
            );
        }
        return orderRepository.findOrdersByAccount_IdOrderByCreatedAtDesc(
                account.getId(),
                PageRequest.of(page, 4)
        );
    }

    @Override
    public Order readOrderByIdAndAccount(Account account, Long orderId) throws DataNotFoundException {
        return orderRepository.findOrderByAccount_IdAndId(
                account.getId(),
                orderId
        ).orElseThrow(() -> new DataNotFoundException("Không tìm thấy đơn hàng"));
    }

    @Override
    public Order cancelOrder(Account account, Long orderId) throws DataNotFoundException {
        Order order = orderRepository.findOrderByAccount_IdAndId(
                account.getId(),
                orderId
        ).orElseThrow(() -> new DataNotFoundException("Không tìm thấy đơn hàng"));
        if (order.getStatus() == OrderStatus.PENDING || order.getStatus() == OrderStatus.PREPARING){
            order.setStatus(OrderStatus.CANCELLED);
            return orderRepository.save(order);
        }
        return order;
    }

    @Override
    public Page<Order> readAllOrders(Integer page) {
        return orderRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, 12));
    }

    @Override
    public Page<Order> readOrdersByStatus(OrderStatus status, Integer page) {
        return orderRepository.findOrdersByStatusOrderByCreatedAtDesc(
                status,
                PageRequest.of(page, 12)
        );
    }

    @Override
    public Order readOrderById(Long orderId) throws DataNotFoundException {
        return orderRepository
                .findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy đơn hàng"));
    }

    @Override
    public void deleteOrderById(Long orderId) throws DataNotFoundException {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy đơn hàng"));
        orderRepository.deleteById(orderId);
    }

    @Override
    public Order updateStatusOfOrderById(Long orderId, OrderStatus status) throws DataNotFoundException {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy đơn hàng"));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Override
    public Map<String, Long> countAllOrders() {
        Long count = orderRepository.count();
        return Map.of("numberOfOrders", count);
    }

    @Override
    public Map<String, Long> countAllOrdersToday() {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = LocalDateTime.of(end.getYear(), end.getMonth(), end.getDayOfMonth(), 0, 0, 0, 0);
        Long count = orderRepository.countOrdersByCreatedAtBetween(start, end);
        return Map.of("numberOfOrdersToday", count);
    }

    @Override
    public List<Map<String, String>> countAllOrdersByStatus() {
        List<Map<String, String>> list = new ArrayList<>();
        for (OrderStatus status : OrderStatus.values()){
            Map<String, String> result = new HashMap<>();
            Long count = orderRepository.countOrdersByStatus(status);
            result.put("status", status.name());
            result.put("quantity", count.toString());
            list.add(result);
        }
        return list;
    }

    @Override
    public List<Map<String, String>> getSalesStatistic() {
        List<Map<String, String>> result = new ArrayList<>();
        int year = LocalDateTime.now().getYear();
        for (Month month : Month.values()){
            Map<String, String> sales_statistic = new HashMap<>();
            LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0, 0);
            LocalDateTime end = start.with(TemporalAdjusters.lastDayOfMonth());
            List<Order> orderList = orderRepository.findOrdersByCreatedAtBetweenAndStatus(start, end, OrderStatus.COMPLETED);
            Long totalOfMonth = orderList
                    .stream()
                    .mapToLong(Order::getTotal)
                    .sum();
            sales_statistic.put("month", month.name());
            sales_statistic.put("total", totalOfMonth.toString());
            result.add(sales_statistic);
        }
        return result;
    }
}
