package com.example.restaurantbackendapplication1.item.model.dto;

import com.example.restaurantbackendapplication1.unit.model.dto.UnitTypeDto;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemDto {
    private Long id;
    private String code;
    private ItemTypeDto itemType;
    private UnitTypeDto unitType;
    private Integer sortOrder;
    private List<ItemLocaleDto> locales;
}
