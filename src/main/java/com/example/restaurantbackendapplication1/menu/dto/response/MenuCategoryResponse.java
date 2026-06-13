package com.example.restaurantbackendapplication1.menu.dto.response;

import com.example.restaurantbackendapplication1.menu.model.dto.MenuCategoryDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MenuCategoryResponse {
    private final MenuCategoryDto menuCategory;

    public MenuCategoryResponse(MenuCategoryDto menuCategory) {
        this.menuCategory = menuCategory;
    }
}
