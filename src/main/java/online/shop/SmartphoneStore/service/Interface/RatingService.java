package online.shop.SmartphoneStore.service.Interface;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.Rating;
import online.shop.SmartphoneStore.entity.payload.RatingStatistic;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.exception.custom.RatingOverLimitException;
import org.springframework.data.domain.Page;

public interface RatingService {

    Rating createRating(Account account, Rating rating) throws DataNotFoundException, RatingOverLimitException;

    Page<Rating> readAllRatingsOfSmartphone(Long smartphoneId, Integer page) throws DataNotFoundException;

    Rating updateRating(Long ratingId, Account account, Rating rating) throws DataNotFoundException;

    void deleteRatingOfAccount(Long ratingId, Account account) throws DataNotFoundException;

    Page<Rating> readRatingsOfAccount(Account account, Integer page);

    Page<Rating> readAllRatings(Integer page);

    void deleteRatingById(Long ratingId) throws DataNotFoundException;

    RatingStatistic readRateValue(Long smartphoneId);

    Rating readRatingOfAccountById(Account account, Long ratingId) throws DataNotFoundException;

    Rating readRatingById(Long ratingId) throws DataNotFoundException;

}
