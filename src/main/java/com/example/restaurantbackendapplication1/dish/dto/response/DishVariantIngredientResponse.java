package com.example.restaurantbackendapplication1.dish.dto.response;

import com.example.restaurantbackendapplication1.dish.model.dto.DishVariantIngredientDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DishVariantIngredientResponse {
    private DishVariantIngredientDto dishVariantIngredient;

    public DishVariantIngredientResponse(DishVariantIngredientDto dishVariantIngredient) {
        this.dishVariantIngredient = dishVariantIngredient;
    }
}
