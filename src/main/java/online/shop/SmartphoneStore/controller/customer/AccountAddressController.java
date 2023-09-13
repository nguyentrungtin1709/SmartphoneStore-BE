package online.shop.SmartphoneStore.controller.customer;

import jakarta.validation.Valid;
import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.Address;
import online.shop.SmartphoneStore.exception.custom.AddressOverLimitException;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.service.AddressServiceImplement;
import online.shop.SmartphoneStore.service.Interface.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account/address")
public class AccountAddressController {

    private final AddressService addressService;

    @Autowired
    public AccountAddressController(AddressServiceImplement addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<Address> saveAddress(
            @CurrentSecurityContext SecurityContext securityContext,
            @Valid @RequestBody Address address
    ) throws AddressOverLimitException {
        address.setAccount(
                (Account) securityContext.getAuthentication().getPrincipal()
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    addressService.saveAddress(address)
                );
    }

    @GetMapping
    public ResponseEntity<List<Address>> readAllAddressOfAccount(
            @CurrentSecurityContext SecurityContext securityContext
    ){
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        addressService.readAllAddressOfAccount(account)
                );
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<Address> readAddressById(
            @CurrentSecurityContext SecurityContext securityContext,
            @PathVariable("addressId") Long addressId
    ) throws DataNotFoundException {
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        addressService.readAddressByAccountAndId(account, addressId)
                );
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<Address> updateAddress(
        @PathVariable("addressId") Long addressId,
        @CurrentSecurityContext SecurityContext securityContext,
        @RequestBody Address address
    ) throws DataNotFoundException {
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        addressService. updateAddress(account, addressId, address)
                );
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Object> deleteAddress(
            @CurrentSecurityContext SecurityContext securityContext,
            @PathVariable("addressId") Long addressId
    ) {
        Account account = (Account) securityContext.getAuthentication().getPrincipal();
        addressService.deleteAddress(account, addressId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(null);
    }
}
