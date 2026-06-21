package com.example.restaurantbackendapplication1.restaurant.dto.response;

import com.example.restaurantbackendapplication1.restaurant.model.dto.RestaurantOperatingHourDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RestaurantOperatingHourResponse {
    private final RestaurantOperatingHourDto restaurantOperatingHour;

    public RestaurantOperatingHourResponse(RestaurantOperatingHourDto restaurantOperatingHour) {
        this.restaurantOperatingHour = restaurantOperatingHour;
    }
}
