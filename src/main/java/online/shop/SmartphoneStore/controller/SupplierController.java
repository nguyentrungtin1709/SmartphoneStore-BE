package online.shop.SmartphoneStore.controller;

import jakarta.validation.Valid;
import online.shop.SmartphoneStore.entity.Supplier;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.exception.custom.UniqueConstraintException;
import online.shop.SmartphoneStore.service.Interface.SupplierService;
import online.shop.SmartphoneStore.service.SupplierServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierServiceImplement supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public ResponseEntity<Supplier> saveSupplier(
            @Valid @RequestBody Supplier supplier
    ) throws UniqueConstraintException {
      return ResponseEntity
              .status(HttpStatus.CREATED)
              .contentType(MediaType.APPLICATION_JSON)
              .body(
                      supplierService.saveSupplier(supplier)
              );
    }

    @GetMapping
    public ResponseEntity<List<Supplier>> readAllSuppliers(){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        supplierService.readAllSuppliers()
                );
    }

    @GetMapping("/{supplierId}")
    public ResponseEntity<Supplier> readSupplierById(
        @PathVariable("supplierId") Integer supplierId
    ) throws DataNotFoundException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        supplierService.readSupplierById(supplierId)
                );
    }

    @PutMapping("/{supplierId}")
    public ResponseEntity<Supplier> updateSupplier(
            @PathVariable("supplierId") Integer supplierId,
            @Valid @RequestBody Supplier supplier
    ) throws UniqueConstraintException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        supplierService.updateSupplier(supplierId, supplier)
                );
    }

    @DeleteMapping("/{supplierId}")
    public ResponseEntity<Object> deleteSupplierById(
            @PathVariable("supplierId") Integer supplierId
    ) throws DataNotFoundException {
        supplierService.deleteSupplierById(supplierId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(null);
    }
}
