package com.example.restaurantbackendapplication1.restaurant.model.projection;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.DayOfWeek;
import java.time.LocalTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface RestaurantOperatingHourSummary {
    Long getId();
    DayOfWeek getDayOfWeek();
    Short getSequenceNo();
    LocalTime getOpenTime();
    LocalTime getCloseTime();
    String getLabel();
    Boolean getClosesNextDay();
    Boolean getIsClosed();
}
