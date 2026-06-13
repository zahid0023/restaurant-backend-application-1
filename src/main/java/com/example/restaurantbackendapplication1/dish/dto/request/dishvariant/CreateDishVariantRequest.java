package com.example.restaurantbackendapplication1.dish.dto.request.dishvariant;

import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantingredient.CreateDishVariantIngredientRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantlocale.CreateDishVariantLocaleRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateDishVariantRequest extends DishVariantRequest {

    @NotBlank
    @Size(max = 50)
    private String code;

    @Valid
    private List<CreateDishVariantLocaleRequest> locales;

    @Valid
    private List<CreateDishVariantIngredientRequest> ingredients;
}
