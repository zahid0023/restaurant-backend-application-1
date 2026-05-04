package com.example.restaurantbackendapplication1.dto.request.dishvariant;

import com.example.restaurantbackendapplication1.dto.request.dishvariantlocale.DishVariantLocaleRequest;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateDishVariantRequest extends DishVariantRequest {

    @Valid
    private List<DishVariantLocaleRequest> locales;
}
