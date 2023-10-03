package online.shop.SmartphoneStore.repository;

import online.shop.SmartphoneStore.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findOrdersByAccount_Id(Long accountId, PageRequest page);

    Optional<Order> findOrderByAccount_IdAndId(Long accountId, Long orderId);
}
