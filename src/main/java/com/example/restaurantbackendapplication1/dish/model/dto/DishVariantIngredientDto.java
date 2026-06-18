package com.example.restaurantbackendapplication1.dish.model.dto;

import com.example.restaurantbackendapplication1.item.model.dto.ItemDto;
import com.example.restaurantbackendapplication1.unit.model.dto.UnitDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DishVariantIngredientDto {
    private Long id;
    private DishVariantDto dishVariant;
    private ItemDto item;
    private BigDecimal quantity;
    private UnitDto unit;
    private Integer sortOrder;
}
