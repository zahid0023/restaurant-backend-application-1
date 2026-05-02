package com.example.restaurantbackendapplication1.dto.request.recipeitem;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RecipeItemRequest {

    @NotNull
    private Long itemId;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 12, fraction = 6)
    private BigDecimal quantity;
}
