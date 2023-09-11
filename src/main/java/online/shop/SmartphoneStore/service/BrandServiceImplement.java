package online.shop.SmartphoneStore.service;

import online.shop.SmartphoneStore.repository.BrandRepository;
import online.shop.SmartphoneStore.service.Interface.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImplement implements BrandService {

    private final BrandRepository brandRepository;

    @Autowired
    public BrandServiceImplement(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }


}
