package com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantclosedday;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.DayOfWeek;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateRestaurantClosedDayRequest {

    @NotNull
    private DayOfWeek dayOfWeek;

    private String note;
}
