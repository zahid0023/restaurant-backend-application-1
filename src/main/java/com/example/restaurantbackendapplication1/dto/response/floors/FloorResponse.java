package com.example.restaurantbackendapplication1.dto.response.floors;

import com.example.restaurantbackendapplication1.model.dto.FloorDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FloorResponse {
    private final FloorDto floor;

    public FloorResponse(FloorDto floor) {
        this.floor = floor;
    }
}
