package com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantclosedday;

import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateRestaurantClosedDayRequest {

    private String note;
}
