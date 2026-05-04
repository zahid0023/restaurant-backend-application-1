package com.example.restaurantbackendapplication1.model.dto;

import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DishDto {
    private Long id;
    private Long menuCategoryId;
    private String code;
    private Integer sortOrder;
    private Boolean isAvailable;
    private Boolean isFeatured;
    private Boolean isVeg;
}
