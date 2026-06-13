package com.example.restaurantbackendapplication1.menu.model.dto;

import java.math.BigDecimal;
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
