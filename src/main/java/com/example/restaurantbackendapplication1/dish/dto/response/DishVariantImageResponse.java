package com.example.restaurantbackendapplication1.dish.dto.response;

import com.example.restaurantbackendapplication1.dish.model.dto.DishVariantImageDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DishVariantImageResponse {
    private final DishVariantImageDto dishVariantImage;

    public DishVariantImageResponse(DishVariantImageDto dishVariantImage) {
        this.dishVariantImage = dishVariantImage;
    }
}
