package com.example.restaurantbackendapplication1.restaurant.dto.response;

import com.example.restaurantbackendapplication1.restaurant.model.dto.RestaurantClosedDayDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RestaurantClosedDayResponse {
    private final RestaurantClosedDayDto restaurantClosedDay;

    public RestaurantClosedDayResponse(RestaurantClosedDayDto restaurantClosedDay) {
        this.restaurantClosedDay = restaurantClosedDay;
    }
}
