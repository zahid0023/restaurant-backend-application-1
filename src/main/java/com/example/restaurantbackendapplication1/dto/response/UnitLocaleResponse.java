package com.example.restaurantbackendapplication1.dto.response;

import com.example.restaurantbackendapplication1.model.dto.UnitLocaleDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UnitLocaleResponse {
    private final UnitLocaleDto unitLocale;

    public UnitLocaleResponse(UnitLocaleDto unitLocale) {
        this.unitLocale = unitLocale;
    }
}
