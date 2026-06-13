package com.example.restaurantbackendapplication1.dto.response;

import com.example.restaurantbackendapplication1.model.dto.DishVariantDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DishVariantResponse {
    private DishVariantDto dishVariant;

    public DishVariantResponse(DishVariantDto dishVariant) {
        this.dishVariant = dishVariant;
    }
}
