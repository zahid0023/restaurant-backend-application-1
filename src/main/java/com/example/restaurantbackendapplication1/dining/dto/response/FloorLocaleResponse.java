package com.example.restaurantbackendapplication1.dining.dto.response;

import com.example.restaurantbackendapplication1.dining.model.dto.FloorLocaleDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FloorLocaleResponse {
    private final FloorLocaleDto floorLocale;

    public FloorLocaleResponse(FloorLocaleDto floorLocale) {
        this.floorLocale = floorLocale;
    }
}
