package com.example.restaurantbackendapplication1.unit.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UnitDto {
    private Long id;
    private Long unitTypeId;
    private String code;
    private Boolean isBase;
    private Integer sortOrder;
    private List<UnitLocaleDto> locales;
}
