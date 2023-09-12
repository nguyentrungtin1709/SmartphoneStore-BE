package online.shop.SmartphoneStore.repository;

import online.shop.SmartphoneStore.entity.Smartphone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SmartphoneRepository extends JpaRepository<Smartphone, Long> {

    Optional<Smartphone> findSmartphoneByName(String name);

    Optional<Smartphone> findSmartphoneBySku(String sku);

}
