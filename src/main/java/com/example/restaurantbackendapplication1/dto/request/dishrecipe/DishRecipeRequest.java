package com.example.restaurantbackendapplication1.dto.request.dishrecipe;

import jakarta.validation.constraints.Size;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DishRecipeRequest {

    @Size(max = 50)
    private String code;
}
