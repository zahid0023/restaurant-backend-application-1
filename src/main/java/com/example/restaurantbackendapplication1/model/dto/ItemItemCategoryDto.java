package com.example.restaurantbackendapplication1.model.dto;

import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemItemCategoryDto {
    private Long id;
    private Long itemId;
    private Long itemCategoryId;
}
