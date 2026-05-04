package com.example.restaurantbackendapplication1.dto.response;

import com.example.restaurantbackendapplication1.model.dto.DishLocaleDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DishLocaleResponse {
    private final DishLocaleDto dishLocale;

    public DishLocaleResponse(DishLocaleDto dishLocale) {
        this.dishLocale = dishLocale;
    }
}
