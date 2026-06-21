package com.example.restaurantbackendapplication1.restaurant.dto.request.schedule;

import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantclosedday.CreateRestaurantClosedDayRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantoperatinghour.CreateRestaurantOperatingHourRequest;
import jakarta.validation.Valid;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ScheduleRequest {

    @Valid
    private List<CreateRestaurantOperatingHourRequest> operating = new ArrayList<>();

    @Valid
    private List<CreateRestaurantClosedDayRequest> closing = new ArrayList<>();
}
