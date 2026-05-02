package com.example.restaurantbackendapplication1.dto.response;

import com.example.restaurantbackendapplication1.model.dto.UnitTypeLocaleDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UnitTypeLocaleResponse {
    private final UnitTypeLocaleDto unitTypeLocale;

    public UnitTypeLocaleResponse(UnitTypeLocaleDto unitTypeLocale) {
        this.unitTypeLocale = unitTypeLocale;
    }
}
