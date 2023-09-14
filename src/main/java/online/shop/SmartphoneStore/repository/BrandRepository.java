package online.shop.SmartphoneStore.repository;

import online.shop.SmartphoneStore.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    Optional<Brand> findBrandByName(String name);

    boolean existsBrandByName(String name);

}
