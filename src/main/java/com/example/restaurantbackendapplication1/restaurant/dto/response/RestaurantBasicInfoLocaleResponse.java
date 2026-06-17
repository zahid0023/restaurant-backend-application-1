package com.example.restaurantbackendapplication1.restaurant.dto.response;

import com.example.restaurantbackendapplication1.restaurant.model.dto.RestaurantBasicInfoLocaleDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RestaurantBasicInfoLocaleResponse {
    private final RestaurantBasicInfoLocaleDto restaurantBasicInfoLocale;

    public RestaurantBasicInfoLocaleResponse(RestaurantBasicInfoLocaleDto restaurantBasicInfoLocale) {
        this.restaurantBasicInfoLocale = restaurantBasicInfoLocale;
    }
}
