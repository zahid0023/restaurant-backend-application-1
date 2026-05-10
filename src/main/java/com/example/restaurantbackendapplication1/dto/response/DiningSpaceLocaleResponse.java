package com.example.restaurantbackendapplication1.dto.response;

import com.example.restaurantbackendapplication1.model.dto.DiningSpaceLocaleDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DiningSpaceLocaleResponse {
    private final DiningSpaceLocaleDto diningSpaceLocale;

    public DiningSpaceLocaleResponse(DiningSpaceLocaleDto diningSpaceLocale) {
        this.diningSpaceLocale = diningSpaceLocale;
    }
}
