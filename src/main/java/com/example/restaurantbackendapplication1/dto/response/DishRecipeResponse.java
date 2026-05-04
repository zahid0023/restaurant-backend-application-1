package com.example.restaurantbackendapplication1.dto.response;

import com.example.restaurantbackendapplication1.model.dto.DishRecipeDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DishRecipeResponse {
    private final DishRecipeDto dishRecipe;

    public DishRecipeResponse(DishRecipeDto dishRecipe) {
        this.dishRecipe = dishRecipe;
    }
}
