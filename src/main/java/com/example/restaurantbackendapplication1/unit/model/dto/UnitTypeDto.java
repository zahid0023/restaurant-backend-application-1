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
public class UnitTypeDto {
    private Long id;
    private String code;
    private Integer sortOrder;
    private List<UnitTypeLocaleDto> locales;
    private List<UnitDto> units;
}
