package com.example.restaurantbackendapplication1.dto.response;

import com.example.restaurantbackendapplication1.model.dto.MenuLocaleDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MenuLocaleResponse {
    private final MenuLocaleDto menuLocale;

    public MenuLocaleResponse(MenuLocaleDto menuLocale) {
        this.menuLocale = menuLocale;
    }
}
