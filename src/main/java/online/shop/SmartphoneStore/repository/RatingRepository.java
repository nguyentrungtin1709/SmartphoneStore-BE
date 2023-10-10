package online.shop.SmartphoneStore.repository;

import online.shop.SmartphoneStore.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    Page<Rating> findRatingsBySmartphone_IdOrderByCreatedAtDesc(Long smartphoneId, PageRequest page);

    boolean existsRatingByAccount_IdAndSmartphone_Id(Long accountId, Long smartphoneId);

    Page<Rating> findRatingsByAccount_IdOrderByCreatedAtDesc(Long accountId, PageRequest pageRequest);

    Page<Rating> findAllByOrderByCreatedAtDesc(PageRequest pageRequest);

    List<Rating> findRatingsBySmartphone_Id(Long smartphoneId);

}
