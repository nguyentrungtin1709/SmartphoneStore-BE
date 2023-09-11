package online.shop.SmartphoneStore.controller;

import online.shop.SmartphoneStore.entity.Supplier;
import online.shop.SmartphoneStore.service.Interface.SupplierService;
import online.shop.SmartphoneStore.service.SupplierServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierServiceImplement supplierService) {
        this.supplierService = supplierService;
    }

}
