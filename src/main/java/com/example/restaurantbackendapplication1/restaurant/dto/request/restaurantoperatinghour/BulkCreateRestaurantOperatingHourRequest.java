package com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantoperatinghour;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BulkCreateRestaurantOperatingHourRequest {

    @NotEmpty
    @Valid
    private List<CreateRestaurantOperatingHourRequest> slots;
}
