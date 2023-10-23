package online.shop.SmartphoneStore.service.Interface;

import online.shop.SmartphoneStore.entity.Enum.Sort;
import online.shop.SmartphoneStore.entity.Smartphone;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.exception.custom.UniqueConstraintException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SmartphoneService {

    Smartphone saveSmartphone(Smartphone smartphone)
            throws UniqueConstraintException, DataNotFoundException;

    Page<Smartphone> readSmartphones(
            Integer page,
            Integer labelId,
            Integer minPrice,
            Integer maxPrice,
            Sort sortType,
            Integer size
    );

    Page<Smartphone> searchSmartphonesByKeyword(String keyword, Integer page, Integer size);

    Smartphone readSmartphoneById(Long smartphoneId) throws DataNotFoundException;

    void deleteSmartphoneById(Long smartphoneId) throws DataNotFoundException, IOException;

    Smartphone updateImage(Long smartphoneId, MultipartFile image)
            throws DataNotFoundException, IOException;

    Smartphone updateInfo(Long smartphoneId, Smartphone smartphone) throws DataNotFoundException, UniqueConstraintException;

    Map<String, Long> countAllSmartphones();

    List<Map<String, String>> countAllSmartphonesByBrand();


    List<Smartphone> findBestSellers(Integer top);

}
