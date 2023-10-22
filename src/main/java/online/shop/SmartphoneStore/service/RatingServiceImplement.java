package online.shop.SmartphoneStore.service;

import online.shop.SmartphoneStore.entity.Account;
import online.shop.SmartphoneStore.entity.Enum.Star;
import online.shop.SmartphoneStore.entity.Rating;
import online.shop.SmartphoneStore.entity.Smartphone;
import online.shop.SmartphoneStore.entity.payload.RatingStatistic;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.exception.custom.RatingOverLimitException;
import online.shop.SmartphoneStore.repository.RatingRepository;
import online.shop.SmartphoneStore.repository.SmartphoneRepository;
import online.shop.SmartphoneStore.service.Interface.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RatingServiceImplement implements RatingService {

    private final SmartphoneRepository smartphoneRepository;

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImplement(
            SmartphoneRepository smartphoneRepository,
            RatingRepository ratingRepository
    ) {
        this.smartphoneRepository = smartphoneRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Rating createRating(Account account, Rating rating) throws DataNotFoundException, RatingOverLimitException {
        Smartphone smartphone = smartphoneRepository
                .findById(rating.getSmartphone().getId())
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy sản phẩm"));
        boolean hadRating = ratingRepository.existsRatingByAccount_IdAndSmartphone_Id(
                account.getId(),
                smartphone.getId()
        );
        if (hadRating){
            throw new RatingOverLimitException("Sản phẩm đã được đánh giá");
        }
        rating.setAccount(account);
        rating.setSmartphone(smartphone);
        return ratingRepository.save(rating);
    }

    @Override
    public Page<Rating> readAllRatingsOfSmartphone(Long smartphoneId, Integer page) throws DataNotFoundException {
        Smartphone smartphone = smartphoneRepository
                .findById(smartphoneId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy sản phẩm"));
        return ratingRepository.findRatingsBySmartphone_IdOrderByCreatedAtDesc(
                smartphone.getId(),
                PageRequest.of(page, 6)
        );
    }

    @Override
    public Rating updateRating(Long ratingId, Account account, Rating rating) throws DataNotFoundException {
        Rating oldRating = ratingRepository
                .findById(ratingId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy đánh giá"));
        if (account.getId().equals(oldRating.getAccount().getId())){
            if (rating.getStar() != null && !rating.getStar().equals(oldRating.getStar())){
                oldRating.setStar(rating.getStar());
            }
            if (rating.getComment() != null && !rating.getComment().equals(oldRating.getComment())){
                oldRating.setComment(rating.getComment());
            }
        }
        return ratingRepository.save(oldRating);
    }

    @Override
    public void deleteRatingOfAccount(Long ratingId, Account account) throws DataNotFoundException {
        Rating rating = ratingRepository
                .findById(ratingId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy đánh giá"));
        if (rating.getAccount().getId().equals(account.getId())){
            ratingRepository.deleteById(ratingId);
        }
    }

    @Override
    public Page<Rating> readRatingsOfAccount(Account account, Integer page) {
        return ratingRepository.findRatingsByAccount_IdOrderByCreatedAtDesc(
                account.getId(),
                PageRequest.of(page, 6)
        );
    }

    @Override
    public Page<Rating> readAllRatings(Integer page) {
        return ratingRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, 12));
    }

    @Override
    public void deleteRatingById(Long ratingId) throws DataNotFoundException {
        Rating rating = ratingRepository
                .findById(ratingId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy đánh giá"));
        ratingRepository.deleteById(ratingId);
    }

    @Override
    public RatingStatistic readRateValue(Long smartphoneId) {
        List<Rating> ratings = ratingRepository.findRatingsBySmartphone_Id(smartphoneId);
        Double rate = (double) ratings.stream()
                .map(Rating::getStar)
                .mapToInt(Star::ordinal)
                .sum() / ratings.size();
        RatingStatistic statistic = RatingStatistic
                .builder()
                .rate(rate)
                .quantity(ratings.size())
                .one(
                    getCount(ratings, Star.ONE)
                ).two(
                    getCount(ratings, Star.TWO)
                ).three(
                    getCount(ratings, Star.THREE)
                ).four(
                    getCount(ratings, Star.FOUR)
                ).five(
                    getCount(ratings, Star.FIVE)
                ).build();
        return statistic;
    }

    public Integer getCount(List<Rating> ratings, Star star){
        return (int) ratings.stream()
                .map(Rating::getStar)
                .filter(item -> item.equals(star))
                .count();
    }

    @Override
    public Rating readRatingOfAccountById(Account account, Long ratingId) throws DataNotFoundException {
        return ratingRepository.findRatingByAccount_IdAndId(
                account.getId(),
                ratingId
        ).orElseThrow(() -> new DataNotFoundException("Không tìm thấy đánh giá"));
    }

    @Override
    public Rating readRatingById(Long ratingId) throws DataNotFoundException {
        return ratingRepository
                .findById(ratingId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy đánh giá"));
    }
}
