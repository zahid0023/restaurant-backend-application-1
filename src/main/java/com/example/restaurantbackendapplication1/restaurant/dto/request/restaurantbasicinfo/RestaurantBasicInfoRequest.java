package com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RestaurantBasicInfoRequest {
    @NotNull
    private Short estd;

    @NotNull
    private Long countryId;

    @NotNull
    private Long cityId;

    private String phone;

    private String email;

    private Double lat;

    private Double lon;
}
