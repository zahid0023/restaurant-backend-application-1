package com.example.restaurantbackendapplication1.dto.response;

import com.example.restaurantbackendapplication1.model.dto.DishVariantLocaleDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DishVariantLocaleResponse {
    private final DishVariantLocaleDto dishVariantLocale;

    public DishVariantLocaleResponse(DishVariantLocaleDto dishVariantLocale) {
        this.dishVariantLocale = dishVariantLocale;
    }
}
