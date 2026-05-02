package com.example.restaurantbackendapplication1.model.dto;

import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemTypeLocaleDto {
    private Long id;
    private Long localeId;
    private String name;
    private String description;
    private Integer sortOrder;
}
