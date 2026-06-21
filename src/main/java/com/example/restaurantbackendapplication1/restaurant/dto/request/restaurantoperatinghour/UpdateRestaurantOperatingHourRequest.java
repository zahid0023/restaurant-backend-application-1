package com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantoperatinghour;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateRestaurantOperatingHourRequest {

    @NotNull
    private DayOfWeek dayOfWeek;

    @NotNull
    @Positive
    private Short sequenceNo;

    @NotNull
    private LocalTime openTime;

    @NotNull
    private LocalTime closeTime;

    @Size(max = 255)
    private String label;

    private Boolean closesNextDay;

    private Boolean isClosed;
}
