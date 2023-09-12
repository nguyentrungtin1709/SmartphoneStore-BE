package online.shop.SmartphoneStore.service;

import online.shop.SmartphoneStore.repository.SmartphoneRepository;
import online.shop.SmartphoneStore.service.Interface.SmartphoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmartphoneServiceImplement implements SmartphoneService {

    private final SmartphoneRepository smartphoneRepository;

    @Autowired
    public SmartphoneServiceImplement(SmartphoneRepository smartphoneRepository) {
        this.smartphoneRepository = smartphoneRepository;
    }


}
