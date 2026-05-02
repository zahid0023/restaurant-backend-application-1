package com.example.restaurantbackendapplication1.dto.response;

import com.example.restaurantbackendapplication1.model.dto.MenuItemDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MenuItemResponse {
    private final MenuItemDto menuItem;

    public MenuItemResponse(MenuItemDto menuItem) {
        this.menuItem = menuItem;
    }
}
