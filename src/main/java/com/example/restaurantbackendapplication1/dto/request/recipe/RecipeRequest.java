package com.example.restaurantbackendapplication1.dto.request.recipe;

import jakarta.validation.constraints.Size;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RecipeRequest {

    @Size(max = 50)
    private String code;
}
