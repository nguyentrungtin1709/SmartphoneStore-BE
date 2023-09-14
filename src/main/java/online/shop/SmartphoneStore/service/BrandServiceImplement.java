package online.shop.SmartphoneStore.service;

import online.shop.SmartphoneStore.entity.Brand;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.exception.custom.UniqueConstraintException;
import online.shop.SmartphoneStore.repository.BrandRepository;
import online.shop.SmartphoneStore.service.Interface.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImplement implements BrandService {

    private final BrandRepository brandRepository;

    @Autowired
    public BrandServiceImplement(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }


    @Override
    public Brand saveBrand(Brand brand)
            throws UniqueConstraintException
    {
        boolean hasBrand = brandRepository.existsBrandByName(brand.getName());
        if (hasBrand){
            throw new UniqueConstraintException(
                    Map.of("name","Nhãn hiệu đã tồn tại")
            );
        }
        return brandRepository.save(brand);
    }

    @Override
    public List<Brand> readAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand readBrandById(Integer brandId) throws DataNotFoundException {
        return brandRepository
                .findById(brandId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy thương hiệu"));
    }

    @Override
    public Brand updateBrand(Integer brandId, Brand brand) throws UniqueConstraintException {
        boolean hasBrand = brandRepository.existsBrandByName(brand.getName());
        if (hasBrand){
            throw new UniqueConstraintException(
                    Map.of("name","Nhãn hiệu đã tồn tại")
            );
        }
        brand.setId(brandId);
        return brandRepository.save(brand);
    }

    @Override
    @Transactional
    public void deleteBrandById(Integer brandId) throws DataNotFoundException{
        brandRepository
                .findById(brandId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy thương hiệu"));
        brandRepository.deleteById(brandId);
    }
}
