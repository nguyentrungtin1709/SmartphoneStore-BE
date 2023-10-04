package online.shop.SmartphoneStore.controller.admin;

import jakarta.validation.Valid;
import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.Order;
import online.shop.SmartphoneStore.entity.payload.ProfileChanging;
import online.shop.SmartphoneStore.entity.payload.RegisterRequest;
import online.shop.SmartphoneStore.entity.payload.TokenResponse;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.exception.custom.UniqueConstraintException;
import online.shop.SmartphoneStore.service.AccountDetailsService;
import online.shop.SmartphoneStore.service.AuthenticationServiceImplement;
import online.shop.SmartphoneStore.service.Interface.AuthenticationService;
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

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/accounts")
public class AdminAccountController {

    private final AccountDetailsService accountDetailsService;

    private final AuthenticationService authenticationService;

    private final OrderService orderService;

    @Autowired
    public AdminAccountController(
            AccountDetailsService accountDetailsService,
            AuthenticationServiceImplement authenticationService,
            OrderServiceImplement orderServiceImplement
    ) {
        this.accountDetailsService = accountDetailsService;
        this.authenticationService = authenticationService;
        this.orderService = orderServiceImplement;
    }

    @GetMapping
    public ResponseEntity<Page<Account>> readAllAccounts(
            @RequestParam(value = "page", defaultValue = "0") Integer page
    ){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    accountDetailsService.readAllAccounts(page)
                );
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> readAccountById(
            @PathVariable("accountId") Long accountId
    ) throws DataNotFoundException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                  accountDetailsService.readAccountById(accountId)
                );
    }

    @GetMapping("/{accountId}/orders")
    public ResponseEntity<Page<Order>> readAllOrdersOfUser(
            @PathVariable("accountId") Long accountId,
            @RequestParam(value = "page", defaultValue = "0") Integer page
    ) throws DataNotFoundException {
        Account account = accountDetailsService.readAccountById(accountId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        orderService.readAllOrders(account, page)
                );
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Map<String, String>> deleteAccount(
            @PathVariable("accountId") Long accountId
    ) throws DataNotFoundException {
        accountDetailsService.deleteAccountById(accountId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        Map.of("message", "Đã xóa tài khoản")
                );
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Account>> searchAccountByEmail(
            @RequestParam("key") String keyword,
            @RequestParam(value = "page", defaultValue = "0") Integer page
    ){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                  accountDetailsService.searchAccountByEmail(keyword, page)
                );
    }

    @PostMapping
    public ResponseEntity<TokenResponse> createAccount(
            @Valid @RequestBody RegisterRequest request
    ) throws UniqueConstraintException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        authenticationService.register(request)
                );
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<Account> changeProfile(
            @PathVariable("accountId") Long accountId,
            @Valid @RequestBody ProfileChanging profile
    ) throws DataNotFoundException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        accountDetailsService.updateProfile(
                                accountId,
                                profile
                        )
                );
    }
}
