package com.example.restaurantbackendapplication1.dish.dto.response;

import com.example.restaurantbackendapplication1.dish.model.dto.DishDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DishResponse {
    private DishDto dish;

    public DishResponse(DishDto dish) {
        this.dish = dish;
    }
}
