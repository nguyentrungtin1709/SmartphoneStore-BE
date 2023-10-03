package online.shop.SmartphoneStore.controller.customer;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.Order;
import online.shop.SmartphoneStore.service.AccountDetailsService;
import online.shop.SmartphoneStore.service.Interface.OrderService;
import online.shop.SmartphoneStore.service.OrderServiceImplement;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account/order")
public class AccountOrderController {
    private final AccountDetailsService accountDetailsService;

    private final OrderService orderService;


    public AccountOrderController(
            AccountDetailsService accountDetailsService,
            OrderServiceImplement orderService
    ) {
        this.accountDetailsService = accountDetailsService;
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(
            @CurrentSecurityContext SecurityContext securityContext,
            @RequestBody Order order
    ){
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        return orderService.createOrder(order, account);
    }
}
