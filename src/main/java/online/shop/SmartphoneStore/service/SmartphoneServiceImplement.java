package online.shop.SmartphoneStore.service;

import online.shop.SmartphoneStore.entity.Brand;
import online.shop.SmartphoneStore.entity.Enum.Sort;
import online.shop.SmartphoneStore.entity.Smartphone;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.exception.custom.UniqueConstraintException;
import online.shop.SmartphoneStore.repository.BrandRepository;
import online.shop.SmartphoneStore.repository.SmartphoneRepository;
import online.shop.SmartphoneStore.service.Interface.FileStorageService;
import online.shop.SmartphoneStore.service.Interface.SmartphoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Service
public class SmartphoneServiceImplement implements SmartphoneService {

    private final SmartphoneRepository smartphoneRepository;

    private final BrandRepository brandRepository;

    private final FileStorageService fileStorageService;

    @Autowired
    public SmartphoneServiceImplement(
            SmartphoneRepository smartphoneRepository,
            BrandRepository brandRepository, FileStorageServiceImplement fileStorageService
    ) {
        this.smartphoneRepository = smartphoneRepository;
        this.brandRepository = brandRepository;
        this.fileStorageService = fileStorageService;
    }


    @Override
    public Smartphone saveSmartphone(
            Smartphone smartphone
    ) throws UniqueConstraintException, DataNotFoundException {
        boolean hasName = smartphoneRepository.existsSmartphoneByName(smartphone.getName());
        boolean hasSku = smartphoneRepository.existsSmartphoneBySku(smartphone.getSku());
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
        Brand brand = brandRepository
                .findById(
                        smartphone.getBrand().getId()
                )
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy thương hiệu"));
        smartphone.setBrand(brand);
        return smartphoneRepository.save(smartphone);
    }

    @Override
    public Page<Smartphone> readSmartphones(
            Integer page,
            Integer brandId,
            Integer minPrice,
            Integer maxPrice,
            Sort sortType,
            Integer size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        if (sortType == Sort.INCREASE){
            if (Objects.isNull(brandId)){
                return smartphoneRepository.findByPriceBetweenOrderByPrice(
                        minPrice,
                        maxPrice,
                        pageRequest
                );
            } else {
                return smartphoneRepository.findByPriceBetweenAndBrand_IdOrderByPrice(
                        minPrice,
                        maxPrice,
                        brandId,
                        pageRequest
                );
            }
        } else if (sortType == Sort.DECREASE) {
            if (Objects.isNull(brandId)){
                return smartphoneRepository.findByPriceBetweenOrderByPriceDesc(
                        minPrice,
                        maxPrice,
                        pageRequest
                );
            } else {
                return smartphoneRepository.findByPriceBetweenAndBrand_IdOrderByPriceDesc(
                        minPrice,
                        maxPrice,
                        brandId,
                        pageRequest
                );
            }
        } else {
            if (Objects.nonNull(brandId)){
                return smartphoneRepository.findByPriceBetweenAndBrand_IdOrderByCreatedAtDesc(
                        minPrice,
                        maxPrice,
                        brandId,
                        pageRequest
                );
            }
        }
        return smartphoneRepository
                .findByPriceBetweenOrderByCreatedAtDesc(minPrice, maxPrice, pageRequest);
    }

    @Override
    public Page<Smartphone> searchSmartphonesByKeyword(String keyword, Integer page, Integer size) {
        return smartphoneRepository.findByKeyword(keyword, PageRequest.of(page, size));
    }

    @Override
    public Smartphone readSmartphoneById(Long smartphoneId) throws DataNotFoundException {
        return smartphoneRepository
                .findById(smartphoneId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy điện thoại"));
    }

    @Override
    public void deleteSmartphoneById(Long smartphoneId) throws DataNotFoundException, IOException, URISyntaxException {
        Smartphone smartphone = smartphoneRepository
                .findById(smartphoneId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy điện thoại"));
        if (smartphone.getImageUrl() != null){
            String path = smartphone.getImageUrl().getPath();
            String fileName = path.substring(path.lastIndexOf("/") + 1);
            fileStorageService.removeFile(fileName);
        };
        smartphoneRepository.deleteById(smartphoneId);
    }

    @Override
    public Smartphone updateImage(Long smartphoneId, MultipartFile image) throws DataNotFoundException, IOException, URISyntaxException {
        Smartphone smartphone = smartphoneRepository
                .findById(smartphoneId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy điện thoại"));
        if (smartphone.getImageUrl() != null){
            String path = smartphone.getImageUrl().getPath();
            String fileName = path.substring(path.lastIndexOf("/") + 1);
            fileStorageService.removeFile(fileName);
        }
        URI imageUrl = fileStorageService.uploadFile(image);
        smartphone.setImageUrl(imageUrl);
        return smartphoneRepository.save(smartphone);
    }

    @Override
    public Smartphone updateInfo(Long smartphoneId, Smartphone data) throws DataNotFoundException, UniqueConstraintException {
        Smartphone smartphone = smartphoneRepository
                .findById(smartphoneId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy điện thoại"));
        if (!smartphone.getName().equals(data.getName()) || !smartphone.getSku().equals(data.getSku())){
            Map<String, String> errors = new HashMap<>();
            boolean hasName = false;
            boolean hasSku = false;
            if (!smartphone.getName().equals(data.getName())){
                hasName = smartphoneRepository.existsSmartphoneByName(data.getName());
                if (hasName){
                    errors.put("name", "Tên điện thoại đã tồn tại");
                }
            }
            if (!smartphone.getSku().equals(data.getSku())){
                hasSku = smartphoneRepository.existsSmartphoneBySku(data.getSku());
                if (hasSku){
                    errors.put("sku", "Mã SKU đã tồn tại");
                }
            }
            if (hasName || hasSku){
                throw new UniqueConstraintException(errors);
            }
        }
        return smartphoneRepository.save(
                updateInfoHelper(smartphone, data)
        );
    }

    private <T> boolean shouldUpdate(T origin, T newData){
        if (origin == null){
            return true;
        }
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

    @Override
    public Map<String, Long> countAllSmartphones() {
        Long total = smartphoneRepository.count();
        return Map.of("numberOfSmartphones", total);
    }

    @Override
    public List<Map<String, String>> countAllSmartphonesByBrand() {
        List<Brand> brands = brandRepository.findAll();
        List<Map<String, String>> result = new ArrayList<>();
        for (Brand brand : brands) {
            Map<String, String> map = new HashMap<>();
            map.put("name", brand.getName());
            map.put(
                    "quantity",
                    smartphoneRepository
                            .countSmartphonesByBrand_Name(brand.getName())
                            .toString()
            );
            result.add(map);
        }
        return result;
    }

    @Override
    public List<Smartphone> findBestSellers(Integer top) {
        return smartphoneRepository.findTopSmartphoneBestSellers(top);
    }
}
