package online.shop.SmartphoneStore.controller;

import online.shop.SmartphoneStore.service.BrandServiceImplement;
import online.shop.SmartphoneStore.service.Interface.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/brands")
public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandServiceImplement brandService) {
        this.brandService = brandService;
    }

}
