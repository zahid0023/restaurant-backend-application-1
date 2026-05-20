package com.example.restaurantbackendapplication1.dto.request.menu;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MenuRequest {

    @NotNull
    private Integer sortOrder;
}
