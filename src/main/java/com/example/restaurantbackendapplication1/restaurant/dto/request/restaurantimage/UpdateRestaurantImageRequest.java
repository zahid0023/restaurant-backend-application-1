package com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantimage;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateRestaurantImageRequest {
    private String caption;

    @NotNull
    private Integer sortOrder;
}
