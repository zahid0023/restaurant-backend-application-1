package com.example.restaurantbackendapplication1.dish.dto.request;

import com.example.restaurantbackendapplication1.dish.dto.request.dishlocale.CreateDishLocaleRequest;
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
public class CreateDishRequest extends DishRequest {
    @NotBlank
    @Size(max = 50)
    private String code;

    @Valid
    private List<CreateDishLocaleRequest> locales;
}
