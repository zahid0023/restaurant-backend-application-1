package com.example.restaurantbackendapplication1.dto.request.dishvariantlocale;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DishVariantLocaleRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    private String description;

    @NotNull
    private Integer sortOrder;
}
