package com.example.restaurantbackendapplication1.dto.request.dishrecipe;

import com.example.restaurantbackendapplication1.dto.request.dishrecipeingredient.DishRecipeIngredientRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateDishRecipeRequest extends DishRecipeRequest {
    @NotEmpty
    @Valid
    private List<DishRecipeIngredientRequest> ingredients;
}
