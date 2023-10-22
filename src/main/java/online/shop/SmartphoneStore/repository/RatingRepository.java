package online.shop.SmartphoneStore.repository;

import online.shop.SmartphoneStore.entity.Enum.Star;
import online.shop.SmartphoneStore.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    Page<Rating> findRatingsBySmartphone_IdOrderByCreatedAtDesc(Long smartphoneId, PageRequest page);

    boolean existsRatingByAccount_IdAndSmartphone_Id(Long accountId, Long smartphoneId);

    Page<Rating> findRatingsByAccount_IdOrderByCreatedAtDesc(Long accountId, PageRequest pageRequest);

    Page<Rating> findAllByOrderByCreatedAtDesc(PageRequest pageRequest);

    List<Rating> findRatingsBySmartphone_Id(Long smartphoneId);

    Optional<Rating> findRatingByAccount_IdAndId(Long accountId, Long ratingId);

    Long countRatingsByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    Long countRatingsByStar(Star star);

}
