package com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfolocale;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateRestaurantBasicInfoLocaleRequest extends RestaurantBasicInfoLocaleRequest {

    @NotNull
    private Long localeId;
}
