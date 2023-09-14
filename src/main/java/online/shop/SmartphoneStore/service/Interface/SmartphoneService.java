package online.shop.SmartphoneStore.service.Interface;

import online.shop.SmartphoneStore.entity.Enum.Sort;
import online.shop.SmartphoneStore.entity.Smartphone;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.exception.custom.UniqueConstraintException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SmartphoneService {

    Smartphone saveSmartphone(Smartphone smartphone, MultipartFile file)
            throws UniqueConstraintException, IOException;

    Page<Smartphone> readSmartphones(
            Integer page,
            Integer labelId,
            Integer minPrice,
            Integer maxPrice,
            Sort sortType
    );

    Smartphone readSmartphoneById(Long smartphoneId) throws DataNotFoundException;

    void deleteSmartphoneById(Long smartphoneId) throws DataNotFoundException, IOException;

    Smartphone updateImage(Long smartphoneId, MultipartFile image)
            throws DataNotFoundException, IOException;

    Smartphone updateInfo(Long smartphoneId, Smartphone smartphone) throws DataNotFoundException;
}
