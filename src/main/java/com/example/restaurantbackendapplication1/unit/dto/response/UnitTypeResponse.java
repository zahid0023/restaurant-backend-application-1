package com.example.restaurantbackendapplication1.unit.dto.response;

import com.example.restaurantbackendapplication1.unit.model.dto.UnitTypeDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UnitTypeResponse {
    private final UnitTypeDto unitType;

    public UnitTypeResponse(UnitTypeDto unitType) {
        this.unitType = unitType;
    }
}
