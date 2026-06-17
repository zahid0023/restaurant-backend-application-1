package com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RestaurantBasicInfoRequest {

    @NotNull
    private Integer sortOrder;

    private String phone;

    private String email;

    private String logoUrl;
}
