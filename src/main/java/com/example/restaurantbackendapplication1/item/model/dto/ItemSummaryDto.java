package com.example.restaurantbackendapplication1.item.model.dto;

import com.example.restaurantbackendapplication1.unit.model.dto.UnitTypeSummaryDto;
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
public class ItemSummaryDto {
    private Long id;
    private String code;
    private UnitTypeSummaryDto unitType;
    private Integer sortOrder;
    private List<ItemLocaleDto> locales;
}
