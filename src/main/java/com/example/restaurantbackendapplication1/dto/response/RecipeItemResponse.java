package com.example.restaurantbackendapplication1.dto.response;

import com.example.restaurantbackendapplication1.model.dto.RecipeItemDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RecipeItemResponse {
    private final RecipeItemDto recipeItem;

    public RecipeItemResponse(RecipeItemDto recipeItem) {
        this.recipeItem = recipeItem;
    }
}
