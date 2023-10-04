package online.shop.SmartphoneStore.controller.admin;

import online.shop.SmartphoneStore.entity.Enum.OrderStatus;
import online.shop.SmartphoneStore.entity.Order;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.service.Interface.OrderService;
import online.shop.SmartphoneStore.service.OrderServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;

    @Autowired
    public AdminOrderController(OrderServiceImplement orderServiceImplement) {
        this.orderService = orderServiceImplement;
    }

    @GetMapping
    public ResponseEntity<Page<Order>> readAllOrders(
            @RequestParam(value = "page", defaultValue = "0") Integer page
    ){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        orderService.readAllOrders(page)
                );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<Order>> readOrdersByStatus(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @PathVariable(value = "status") OrderStatus status
    ){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        orderService.readOrdersByStatus(status, page)
                );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> readOrderById(
            @PathVariable("orderId") Long orderId
    ) throws DataNotFoundException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        orderService.readOrderById(orderId)
                );
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Map<String, String>> deleteOrderById(
            @PathVariable("orderId") Long orderId
    ) throws DataNotFoundException {
        orderService.deleteOrderById(orderId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        Map.of("message", "Đã xóa đơn hàng")
                );
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateStatusOfOrderById(
            @PathVariable("orderId") Long orderId,
            @RequestParam("status-value") OrderStatus status
    ) throws DataNotFoundException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    orderService.updateStatusOfOrderById(orderId, status)
                );
    }
}
