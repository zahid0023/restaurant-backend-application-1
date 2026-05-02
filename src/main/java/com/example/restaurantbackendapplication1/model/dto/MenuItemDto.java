package com.example.restaurantbackendapplication1.model.dto;

import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MenuItemDto {
    private Long id;
    private Long menuSectionId;
    private String code;
    private Integer sortOrder;
    private BigDecimal price;
    private Boolean isAvailable;
    private Boolean isFeatured;
    private Boolean isVeg;
}
