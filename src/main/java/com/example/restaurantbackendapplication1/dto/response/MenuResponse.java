package com.example.restaurantbackendapplication1.dto.response;

import com.example.restaurantbackendapplication1.model.dto.MenuDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MenuResponse {
    private final MenuDto menu;

    public MenuResponse(MenuDto menu) {
        this.menu = menu;
    }
}
