package com.example.restaurantbackendapplication1.dto.request.dish;

import com.example.restaurantbackendapplication1.dto.request.dishlocale.DishLocaleRequest;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateDishRequest extends DishRequest {

    @Valid
    private List<DishLocaleRequest> locales;
}
