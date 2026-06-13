package com.example.restaurantbackendapplication1.menu.dto.response;

import com.example.restaurantbackendapplication1.menu.model.dto.MenuCategoryLocaleDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MenuCategoryLocaleResponse {
    private final MenuCategoryLocaleDto menuCategoryLocale;

    public MenuCategoryLocaleResponse(MenuCategoryLocaleDto menuCategoryLocale) {
        this.menuCategoryLocale = menuCategoryLocale;
    }
}
