package online.shop.SmartphoneStore.controller.customer;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.Enum.OrderStatus;
import online.shop.SmartphoneStore.entity.Order;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.service.AccountDetailsService;
import online.shop.SmartphoneStore.service.Interface.OrderService;
import online.shop.SmartphoneStore.service.OrderServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account/order")
public class AccountOrderController {
    private final AccountDetailsService accountDetailsService;

    private final OrderService orderService;


    @Autowired
    public AccountOrderController(
            AccountDetailsService accountDetailsService,
            OrderServiceImplement orderService
    ) {
        this.accountDetailsService = accountDetailsService;
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @CurrentSecurityContext SecurityContext securityContext,
            @RequestBody Order order
    ){
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    orderService.createOrder(order, account)
                );
    }

    @GetMapping
    public ResponseEntity<Page<Order>> readAllOrders(
            @CurrentSecurityContext SecurityContext securityContext,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "status", required = false) OrderStatus status
    ){
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        orderService.readAllOrdersByAccount(account, page, status)
                );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> readOrder(
        @CurrentSecurityContext SecurityContext securityContext,
        @PathVariable("orderId") Long orderId
    ) throws DataNotFoundException {
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        orderService.readOrderByIdAndAccount(account, orderId)
                );
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<Order> cancelOrder(
            @CurrentSecurityContext SecurityContext securityContext,
            @PathVariable("orderId") Long orderId
    ) throws DataNotFoundException {
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        orderService.cancelOrder(account, orderId)
                );
    }
}
