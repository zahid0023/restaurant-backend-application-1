package com.example.restaurantbackendapplication1.dto.request.unit;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UnitRequest {

    @NotNull
    private Boolean isBase;

    @NotNull
    private Integer sortOrder;
}
