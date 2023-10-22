package online.shop.SmartphoneStore.repository;

import online.shop.SmartphoneStore.entity.Smartphone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface SmartphoneRepository extends JpaRepository<Smartphone, Long> {

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

    @Query(
            value = "SELECT * FROM dien_thoai WHERE ten_dien_thoai LIKE %:keyword%",
            nativeQuery = true
    )
    Page<Smartphone> findByKeyword(String keyword, PageRequest page);

    Long countSmartphonesByBrand_Name(String brandName);

    @Query(
        value = "SELECT dt.ma_dien_thoai, chip, ngay_them, camera_truoc, hinh_anh, ten_dien_thoai, he_dieu_hanh, pin_sac, dt.gia_ban, dt.so_luong, ram, camera_sau, man_hinh, sim, sku, dung_luong, ma_hang\n" +
                "FROM chi_tiet_don_hang AS ct\n" +
                "JOIN dien_thoai AS dt\n" +
                "ON dt.ma_dien_thoai = ct.ma_dien_thoai\n" +
                "GROUP BY ct.ma_dien_thoai\n" +
                "ORDER BY COUNT(*) DESC\n" +
                "LIMIT :top",
        nativeQuery = true
    )
    List<Smartphone> findTopSmartphoneBestSellers(Integer top);
}
