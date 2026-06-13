package com.example.restaurantbackendapplication1.dining.dto.request.diningspace;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DiningSpaceRequest {
    @NotNull
    private Integer sortOrder;

    @NotNull
    private Integer capacity;

    @NotNull
    private Boolean isBookable;
}
