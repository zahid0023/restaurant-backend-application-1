package com.example.restaurantbackendapplication1.dto.request.itemtype;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemTypeRequest {

    @NotNull
    private Boolean isConsumable;

    @NotNull
    private Integer sortOrder;
}
