package com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfo;

import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfolocale.CreateRestaurantBasicInfoLocaleRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateRestaurantBasicInfoRequest extends RestaurantBasicInfoRequest {

    @NotBlank
    @Size(max = 50)
    private String code;

    @NotNull
    private Long countryId;

    @NotNull
    private Long cityId;

    private List<CreateRestaurantBasicInfoLocaleRequest> locales;
}
