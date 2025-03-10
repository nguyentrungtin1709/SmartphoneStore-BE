package online.shop.SmartphoneStore.repository;

import online.shop.SmartphoneStore.entity.Enum.OrderStatus;
import online.shop.SmartphoneStore.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findOrdersByAccount_IdOrderByCreatedAtDesc(Long accountId, PageRequest page);

    Page<Order> findOrdersByAccount_IdAndStatusOrderByCreatedAtDesc(Long accountId,OrderStatus status, PageRequest page);

    Optional<Order> findOrderByAccount_IdAndId(Long accountId, Long orderId);

    Page<Order> findAllByOrderByCreatedAtDesc(PageRequest page);

    Page<Order> findOrdersByStatusOrderByCreatedAtDesc(OrderStatus status, PageRequest page);

    Long countOrdersByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    Long countOrdersByStatus(OrderStatus status);

    List<Order> findOrdersByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<Order> findOrdersByCreatedAtBetweenAndStatus(LocalDateTime start, LocalDateTime end, OrderStatus status);
}
