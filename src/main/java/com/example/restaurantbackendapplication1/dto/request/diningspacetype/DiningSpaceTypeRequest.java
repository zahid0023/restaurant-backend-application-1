package com.example.restaurantbackendapplication1.dto.request.diningspacetype;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DiningSpaceTypeRequest {

    @NotNull
    private Integer sortOrder;
}
