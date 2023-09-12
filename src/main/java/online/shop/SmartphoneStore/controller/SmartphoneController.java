package online.shop.SmartphoneStore.controller;

import online.shop.SmartphoneStore.service.Interface.SmartphoneService;
import online.shop.SmartphoneStore.service.SmartphoneServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/smartphones")
public class SmartphoneController {

    private final SmartphoneService smartphoneService;

    @Autowired
    public SmartphoneController(SmartphoneServiceImplement smartphoneService) {
        this.smartphoneService = smartphoneService;
    }
}
