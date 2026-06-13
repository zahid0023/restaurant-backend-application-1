package com.example.restaurantbackendapplication1.dining.dto.request.floor;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FloorRequest {

    @NotNull
    private Integer sortOrder;
}
