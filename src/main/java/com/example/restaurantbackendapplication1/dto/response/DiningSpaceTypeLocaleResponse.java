package com.example.restaurantbackendapplication1.dto.response;

import com.example.restaurantbackendapplication1.model.dto.DiningSpaceTypeLocaleDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DiningSpaceTypeLocaleResponse {
    private final DiningSpaceTypeLocaleDto diningSpaceTypeLocale;

    public DiningSpaceTypeLocaleResponse(DiningSpaceTypeLocaleDto diningSpaceTypeLocale) {
        this.diningSpaceTypeLocale = diningSpaceTypeLocale;
    }
}
