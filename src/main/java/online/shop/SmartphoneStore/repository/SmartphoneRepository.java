package online.shop.SmartphoneStore.repository;

import online.shop.SmartphoneStore.entity.Smartphone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SmartphoneRepository extends JpaRepository<Smartphone, Long> {

    Optional<Smartphone> findSmartphoneByName(String name);

    Optional<Smartphone> findSmartphoneBySku(String sku);

    boolean existsSmartphoneByName(String name);

    boolean existsSmartphoneBySku(String sku);

    Page<Smartphone> findByPriceBetweenOrderByCreatedAtDesc(
            Integer minPrice, Integer maxPrice, PageRequest page
    );

    Page<Smartphone> findByPriceBetweenAndBrand_IdOrderByCreatedAtDesc(
            Integer minPrice, Integer maxPrice, Integer brandId, PageRequest page
    );

    Page<Smartphone> findByPriceBetweenOrderByPrice(
            Integer minPrice, Integer maxPrice, PageRequest page
    );

    Page<Smartphone> findByPriceBetweenAndBrand_IdOrderByPrice(
            Integer minPrice, Integer maxPrice, Integer brandId, PageRequest page
    );

    Page<Smartphone> findByPriceBetweenOrderByPriceDesc(
            Integer minPrice, Integer maxPrice, PageRequest page
    );

    Page<Smartphone> findByPriceBetweenAndBrand_IdOrderByPriceDesc(
            Integer minPrice, Integer maxPrice, Integer brandId, PageRequest page
    );

}
