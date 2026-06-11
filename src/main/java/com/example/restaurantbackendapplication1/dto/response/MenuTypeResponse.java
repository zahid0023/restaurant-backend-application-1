package com.example.restaurantbackendapplication1.dto.response;

import com.example.restaurantbackendapplication1.model.dto.MenuTypeDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MenuTypeResponse {
    private final MenuTypeDto menu;

    public MenuTypeResponse(MenuTypeDto menu) {
        this.menu = menu;
    }
}
