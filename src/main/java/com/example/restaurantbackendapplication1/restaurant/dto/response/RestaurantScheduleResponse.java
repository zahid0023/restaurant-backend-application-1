package com.example.restaurantbackendapplication1.restaurant.dto.response;

import com.example.restaurantbackendapplication1.restaurant.model.projection.RestaurantClosedDaySummary;
import com.example.restaurantbackendapplication1.restaurant.model.projection.RestaurantOperatingHourSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RestaurantScheduleResponse {
    private List<RestaurantClosedDaySummary> closing;
    private Map<DayOfWeek, List<RestaurantOperatingHourSummary>> operating;
}
