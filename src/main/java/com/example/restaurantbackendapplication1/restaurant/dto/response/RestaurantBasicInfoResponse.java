package com.example.restaurantbackendapplication1.restaurant.dto.response;

import com.example.restaurantbackendapplication1.restaurant.model.dto.RestaurantBasicInfoDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RestaurantBasicInfoResponse {
    private final RestaurantBasicInfoDto restaurantBasicInfo;

    public RestaurantBasicInfoResponse(RestaurantBasicInfoDto restaurantBasicInfo) {
        this.restaurantBasicInfo = restaurantBasicInfo;
    }
}
