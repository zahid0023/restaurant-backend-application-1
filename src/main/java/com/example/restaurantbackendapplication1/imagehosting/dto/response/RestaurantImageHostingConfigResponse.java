package com.example.restaurantbackendapplication1.imagehosting.dto.response;

import com.example.restaurantbackendapplication1.imagehosting.model.dto.RestaurantImageHostingConfigDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RestaurantImageHostingConfigResponse {
    private final RestaurantImageHostingConfigDto restaurantImageHostingConfig;

    public RestaurantImageHostingConfigResponse(RestaurantImageHostingConfigDto restaurantImageHostingConfig) {
        this.restaurantImageHostingConfig = restaurantImageHostingConfig;
    }
}
