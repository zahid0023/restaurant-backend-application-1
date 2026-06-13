package com.example.restaurantbackendapplication1.dto.request.dishvariant;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DishVariantRequest {

    @NotNull
    private Integer sortOrder;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal price;

    @NotNull
    private Boolean isDefault;

    @NotNull
    private Boolean isVeg;
}
