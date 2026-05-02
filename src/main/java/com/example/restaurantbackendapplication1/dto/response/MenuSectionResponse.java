package com.example.restaurantbackendapplication1.dto.response;

import com.example.restaurantbackendapplication1.model.dto.MenuSectionDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MenuSectionResponse {
    private final MenuSectionDto menuSection;

    public MenuSectionResponse(MenuSectionDto menuSection) {
        this.menuSection = menuSection;
    }
}
