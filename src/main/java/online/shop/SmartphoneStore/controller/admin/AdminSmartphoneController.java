package online.shop.SmartphoneStore.controller.admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import online.shop.SmartphoneStore.entity.Brand;
import online.shop.SmartphoneStore.entity.Smartphone;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.exception.custom.UniqueConstraintException;
import online.shop.SmartphoneStore.service.Interface.SmartphoneService;
import online.shop.SmartphoneStore.service.SmartphoneServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/admin/smartphones")
@Validated
public class AdminSmartphoneController {

    private final SmartphoneService smartphoneService;

    @Autowired
    public AdminSmartphoneController(SmartphoneServiceImplement smartphoneService) {
        this.smartphoneService = smartphoneService;
    }

    @PostMapping
    public ResponseEntity<Smartphone> saveSmartphone(
            @RequestParam("name") @NotBlank(message = "Không được bỏ trống") String name,
            @RequestParam("brandId") Integer brandId,
            @RequestParam("price") @Positive(message = "Giá bán cần lớn hơn 0") Integer price,
            @RequestParam(value = "quantity")
            @PositiveOrZero(message = "Số lượng cần lớn hơn hoặc bằng 0") Integer quantityInStock,
            @RequestParam(value = "screen", required = false) String screen,
            @RequestParam(value = "os", required = false) String operatingSystem,
            @RequestParam(value = "rearCamera", required = false) String rearCamera,
            @RequestParam(value = "frontCamera", required = false) String frontCamera,
            @RequestParam(value = "chip", required = false) String chip,
            @RequestParam(value = "ram", required = false) String ram,
            @RequestParam(value = "storage", required = false) String storageCapacity,
            @RequestParam(value = "sim", required = false) String sim,
            @RequestParam(value = "pin", required = false) String pin,
            @RequestParam("sku") @NotBlank(message = "Không được bỏ trống") String sku,
            @RequestParam("image") MultipartFile image
    ) throws UniqueConstraintException, IOException {
        Smartphone smartphone = Smartphone.builder()
                .name(name)
                .brand(
                        Brand.builder()
                                .id(brandId)
                                .build()
                )
                .price(price)
                .quantityInStock(quantityInStock)
                .screen(screen)
                .operatingSystem(operatingSystem)
                .rearCamera(rearCamera)
                .frontCamera(frontCamera)
                .chip(chip)
                .ram(ram)
                .storageCapacity(storageCapacity)
                .sim(sim)
                .pin(pin)
                .sku(sku)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        smartphoneService.saveSmartphone(smartphone, image)
                );
    }

    @DeleteMapping("/{smartphoneId}")
    public ResponseEntity<Object> deleteSmartphoneById(
            @PathVariable("smartphoneId") Long smartphoneId
    ) throws DataNotFoundException, IOException {
        smartphoneService.deleteSmartphoneById(smartphoneId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(null);
    }

    @PutMapping("/{smartphoneId}/image")
    public ResponseEntity<Smartphone> updateImage(
            @PathVariable("smartphoneId") Long smartphoneId,
            @RequestParam("image") MultipartFile image
    ) throws DataNotFoundException, IOException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        smartphoneService.updateImage(smartphoneId, image)
                );
    }

    @PutMapping("/{smartphoneId}/info")
    public ResponseEntity<Smartphone> updateInfo(
            @PathVariable("smartphoneId") Long smartphoneId,
            @Valid @RequestBody Smartphone smartphone
    ) throws DataNotFoundException, UniqueConstraintException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        smartphoneService.updateInfo(smartphoneId, smartphone)
                );
    }

}
