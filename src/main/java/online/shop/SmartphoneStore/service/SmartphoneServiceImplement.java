package online.shop.SmartphoneStore.service;

import jakarta.validation.Valid;
import online.shop.SmartphoneStore.entity.Brand;
import online.shop.SmartphoneStore.entity.Smartphone;
import online.shop.SmartphoneStore.exception.custom.UniqueConstraintException;
import online.shop.SmartphoneStore.repository.SmartphoneRepository;
import online.shop.SmartphoneStore.service.Interface.BrandService;
import online.shop.SmartphoneStore.service.Interface.FileStorageService;
import online.shop.SmartphoneStore.service.Interface.SmartphoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
public class SmartphoneServiceImplement implements SmartphoneService {

    private final SmartphoneRepository smartphoneRepository;

    private final FileStorageService fileStorageService;

    private final BrandService brandService;

    @Autowired
    public SmartphoneServiceImplement(
            SmartphoneRepository smartphoneRepository,
            FileStorageServiceImplement fileStorageService,
            BrandServiceImplement brandService
    ) {
        this.smartphoneRepository = smartphoneRepository;
        this.fileStorageService = fileStorageService;
        this.brandService = brandService;
    }


    @Override
    public Smartphone saveSmartphone(
            Smartphone smartphone,
            MultipartFile file
    ) throws UniqueConstraintException, IOException {
        boolean hasName = smartphoneRepository.findSmartphoneByName(smartphone.getName()).isPresent();
        boolean hasSku = smartphoneRepository.findSmartphoneBySku(smartphone.getSku()).isPresent();
        if (hasName || hasSku){
            Map<String, String> columns = new HashMap<>();
            if (hasName){
                columns.put("name", "Tên điện thoại đã tồn tại");
            }
            if (hasSku){
                columns.put("sku", "Mã sku đã tồn tại");
            }
            throw new UniqueConstraintException(columns);
        }
        if (!file.isEmpty()){
            URI imageUrl = fileStorageService.uploadFile(file);
            smartphone.setImageUrl(imageUrl);
        }
        return smartphoneRepository.save(smartphone);
    }

    @Override
    public Page<Smartphone> readSmartphones(Integer page) {
        return smartphoneRepository.findAll(PageRequest.of(page, 10));
    }
}
