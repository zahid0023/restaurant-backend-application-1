package com.example.restaurantbackendapplication1.dish.dto.response;

import com.example.restaurantbackendapplication1.dish.model.dto.DishLocaleDto;
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
