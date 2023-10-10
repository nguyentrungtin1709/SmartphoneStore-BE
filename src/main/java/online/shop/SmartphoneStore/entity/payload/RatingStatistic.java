package online.shop.SmartphoneStore.entity.payload;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingStatistic {
    private Double rate;
    private Integer one;
    private Integer two;
    private Integer three;
    private Integer four;
    private Integer five;
    private Integer quantity;

}
