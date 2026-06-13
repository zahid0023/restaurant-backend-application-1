package com.example.restaurantbackendapplication1.menu.model.dto;

import com.example.restaurantbackendapplication1.dish.model.dto.DishDto;
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
public class MenuCategoryDishDto {
    private Long id;
    private Long menuCategoryId;
    private DishDto dish;
}
