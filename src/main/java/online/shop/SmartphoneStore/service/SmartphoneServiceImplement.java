package online.shop.SmartphoneStore.service;

import online.shop.SmartphoneStore.entity.Smartphone;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
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
import java.util.UUID;

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

    @Override
    public Smartphone readSmartphoneById(Long smartphoneId) throws DataNotFoundException {
        return smartphoneRepository
                .findById(smartphoneId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy điện thoại"));
    }

    @Override
    public void deleteSmartphoneById(Long smartphoneId) throws DataNotFoundException {
        smartphoneRepository
                .findById(smartphoneId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy điện thoại"));
        smartphoneRepository.deleteById(smartphoneId);
    }

    @Override
    public Smartphone updateImage(Long smartphoneId, MultipartFile image) throws DataNotFoundException, IOException {
        Smartphone smartphone = smartphoneRepository
                .findById(smartphoneId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy điện thoại"));
        if (smartphone.getImageUrl() != null){
            String path = smartphone.getImageUrl().getPath();
            UUID uuid = UUID.fromString(
                    path.substring(
                            path.lastIndexOf("/") + 1
                    )
            );
            fileStorageService.removeFile(uuid);
        }
        URI imageUrl = fileStorageService.uploadFile(image);
        smartphone.setImageUrl(imageUrl);
        return smartphoneRepository.save(smartphone);
    }

    @Override
    public Smartphone updateInfo(Long smartphoneId, Smartphone data) throws DataNotFoundException {
        Smartphone smartphone = smartphoneRepository
                .findById(smartphoneId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy điện thoại"));
        return smartphoneRepository.save(
                updateInfoHelper(smartphone, data)
        );
    }

    private <T> boolean shouldUpdate(T origin, T newData){
        return !origin.equals(newData);
    }

    private Smartphone updateInfoHelper(Smartphone smartphone, Smartphone data){
        if (shouldUpdate(smartphone.getName(), data.getName())){
            smartphone.setName(data.getName());
        }

        if (shouldUpdate(smartphone.getBrand().getId(), data.getBrand().getId())){
            smartphone.setBrand(data.getBrand());
        }

        if (shouldUpdate(smartphone.getPrice(), data.getPrice())){
            smartphone.setPrice(data.getPrice());
        }

        if (shouldUpdate(smartphone.getQuantityInStock(), data.getQuantityInStock())){
            smartphone.setQuantityInStock(data.getQuantityInStock());
        }

        if (shouldUpdate(smartphone.getScreen(), data.getScreen())){
            smartphone.setScreen(data.getScreen());
        }

        if (shouldUpdate(smartphone.getOperatingSystem(), data.getOperatingSystem())){
            smartphone.setOperatingSystem(data.getOperatingSystem());
        }

        if (shouldUpdate(smartphone.getRearCamera(), data.getRearCamera())){
            smartphone.setRearCamera(data.getRearCamera());
        }

        if (shouldUpdate(smartphone.getFrontCamera(), data.getFrontCamera())){
            smartphone.setFrontCamera(data.getFrontCamera());
        }

        if (shouldUpdate(smartphone.getChip(), data.getChip())){
            smartphone.setChip(data.getChip());
        }

        if (shouldUpdate(smartphone.getRam(), data.getRam())){
            smartphone.setRam(data.getRam());
        }

        if (shouldUpdate(smartphone.getStorageCapacity(), data.getStorageCapacity())){
            smartphone.setStorageCapacity(data.getStorageCapacity());
        }

        if (shouldUpdate(smartphone.getSim(), data.getSim())){
            smartphone.setSim(data.getSim());
        }

        if (shouldUpdate(smartphone.getPin(), data.getPin())){
            smartphone.setPin(data.getPin());
        }

        if (shouldUpdate(smartphone.getSku(), data.getSku())){
            smartphone.setSku(data.getSku());
        }
        return smartphone;
    }
}
