package com.example.restaurantbackendapplication1.restaurant.dto.response;

import com.example.restaurantbackendapplication1.restaurant.model.dto.RestaurantImageDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RestaurantImageResponse {
    private final RestaurantImageDto restaurantImage;

    public RestaurantImageResponse(RestaurantImageDto restaurantImage) {
        this.restaurantImage = restaurantImage;
    }
}
