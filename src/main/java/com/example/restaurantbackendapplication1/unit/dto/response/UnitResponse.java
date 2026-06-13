package com.example.restaurantbackendapplication1.unit.dto.response;

import com.example.restaurantbackendapplication1.unit.model.dto.UnitDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UnitResponse {
    private final UnitDto unit;

    public UnitResponse(UnitDto unit) {
        this.unit = unit;
    }
}
