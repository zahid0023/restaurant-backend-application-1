package com.example.restaurantbackendapplication1.dish.dto.request.dishvariantingredient;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DishVariantIngredientRequest {

    @NotNull
    @DecimalMin("0.000")
    private BigDecimal quantity;

    @NotNull
    private Long unitId;

    @NotNull
    private Integer sortOrder;
}
