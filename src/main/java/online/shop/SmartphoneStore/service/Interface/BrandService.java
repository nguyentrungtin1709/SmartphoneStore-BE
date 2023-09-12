package online.shop.SmartphoneStore.service.Interface;

import online.shop.SmartphoneStore.entity.Brand;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.exception.custom.UniqueConstraintException;

import java.util.List;

public interface BrandService {

    Brand saveBrand(Brand brand) throws UniqueConstraintException;

    List<Brand> readAllBrands();

    Brand readBrandById(Integer brandId) throws DataNotFoundException;

    Brand updateBrand(Integer brandId, Brand brand) throws UniqueConstraintException;

    void deleteBrandById(Integer brandId) throws DataNotFoundException;

}
