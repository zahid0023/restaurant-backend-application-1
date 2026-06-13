package com.example.restaurantbackendapplication1.dining.dto.response;

import com.example.restaurantbackendapplication1.dining.model.dto.DiningSpaceDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DiningSpaceResponse {
    private final DiningSpaceDto diningSpace;

    public DiningSpaceResponse(DiningSpaceDto diningSpace) {
        this.diningSpace = diningSpace;
    }
}
