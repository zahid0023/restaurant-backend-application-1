package com.example.restaurantbackendapplication1.dto.response;

import com.example.restaurantbackendapplication1.model.dto.DishRecipeIngredientDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DishRecipeIngredientResponse {
    private final DishRecipeIngredientDto dishRecipeIngredient;

    public DishRecipeIngredientResponse(DishRecipeIngredientDto dishRecipeIngredient) {
        this.dishRecipeIngredient = dishRecipeIngredient;
    }
}
