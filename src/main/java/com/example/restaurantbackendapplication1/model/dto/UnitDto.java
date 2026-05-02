package com.example.restaurantbackendapplication1.model.dto;

import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UnitDto {
    private Long id;
    private Long unitTypeId;
    private String code;
    private Boolean isBase;
    private Integer sortOrder;
}
