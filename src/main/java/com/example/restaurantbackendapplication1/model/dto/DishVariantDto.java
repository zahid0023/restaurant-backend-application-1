package com.example.restaurantbackendapplication1.model.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DishVariantDto {
    private Long id;
    private Long dishId;
    private String code;
    private Integer sortOrder;
    private BigDecimal price;
    private Boolean isDefault;
    private Boolean isVeg;
    private List<DishVariantLocaleDto> locales;
    private List<DishVariantIngredientDto> ingredients;
}
