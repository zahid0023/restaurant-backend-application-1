package com.example.restaurantbackendapplication1.restaurant.model.projection;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.DayOfWeek;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface RestaurantClosedDaySummary {
    Long getId();
    DayOfWeek getDayOfWeek();
    String getNote();
}
