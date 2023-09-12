package online.shop.SmartphoneStore.controller;

import jakarta.validation.Valid;
import online.shop.SmartphoneStore.entity.Brand;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.exception.custom.UniqueConstraintException;
import online.shop.SmartphoneStore.service.BrandServiceImplement;
import online.shop.SmartphoneStore.service.Interface.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandServiceImplement brandService) {
        this.brandService = brandService;
    }

    @PostMapping("/admin")
    public ResponseEntity<Brand> saveBrand(
            @Valid @RequestBody Brand brand
    ) throws UniqueConstraintException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        brandService.saveBrand(brand)
                );
    }

    @PutMapping("/admin/{brandId}")
    public ResponseEntity<Brand> updateBrand(
            @PathVariable("brandId") Integer brandId,
            @Valid @RequestBody Brand brand
    ) throws UniqueConstraintException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        brandService.updateBrand(brandId, brand)
                );
    }

    @DeleteMapping("/admin/{brandId}")
    public ResponseEntity<Object> deleteBrandById(
            @PathVariable("brandId") Integer brandId
    ) throws DataNotFoundException {
        brandService.deleteBrandById(brandId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(null);
    }

    @GetMapping("/public")
    public ResponseEntity<List<Brand>> readAllBrands(){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        brandService.readAllBrands()
                );
    }

    @GetMapping("/public/{brandId}")
    public ResponseEntity<Brand> readBrandById(
            @PathVariable("brandId") Integer brandId
    ) throws DataNotFoundException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        brandService.readBrandById(brandId)
                );
    }
}
