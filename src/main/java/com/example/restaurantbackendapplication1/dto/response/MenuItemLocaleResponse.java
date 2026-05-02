package com.example.restaurantbackendapplication1.dto.response;

import com.example.restaurantbackendapplication1.model.dto.MenuItemLocaleDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MenuItemLocaleResponse {
    private final MenuItemLocaleDto menuItemLocale;

    public MenuItemLocaleResponse(MenuItemLocaleDto menuItemLocale) {
        this.menuItemLocale = menuItemLocale;
    }
}
