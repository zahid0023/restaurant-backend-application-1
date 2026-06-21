package com.example.restaurantbackendapplication1.restaurant.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RestaurantOperatingHourDto {
    private Long id;
    private DayOfWeek dayOfWeek;
    private Short sequenceNo;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String label;
    private Boolean closesNextDay;
    private Boolean isClosed;
}
