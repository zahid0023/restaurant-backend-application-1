package com.example.restaurantbackendapplication1.menu.dto.response;

import com.example.restaurantbackendapplication1.menu.model.dto.MenuCategoryDishDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MenuCategoryDishResponse {
    private final MenuCategoryDishDto menuCategoryDish;

    public MenuCategoryDishResponse(MenuCategoryDishDto menuCategoryDish) {
        this.menuCategoryDish = menuCategoryDish;
    }
}
