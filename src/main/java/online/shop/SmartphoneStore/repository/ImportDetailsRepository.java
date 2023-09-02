package online.shop.SmartphoneStore.repository;

import online.shop.SmartphoneStore.entity.ImportDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportDetailsRepository extends JpaRepository<ImportDetails, Long> {

}
