package com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfolocale;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RestaurantBasicInfoLocaleRequest {

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    private Short estd;

    @Size(max = 1024)
    private String shortDescription;

    private Double lat;

    private Double lon;

    private String address;

    @NotNull
    private Integer sortOrder;
}
