package com.example.restaurantbackendapplication1.dining.dto.response;

import com.example.restaurantbackendapplication1.dining.model.dto.DiningSpaceTypeDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DiningSpaceTypeResponse {
    private final DiningSpaceTypeDto diningSpaceType;

    public DiningSpaceTypeResponse(DiningSpaceTypeDto diningSpaceType) {
        this.diningSpaceType = diningSpaceType;
    }
}
