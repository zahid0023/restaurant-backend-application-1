package com.example.restaurantbackendapplication1.dto.response;

import com.example.restaurantbackendapplication1.model.dto.MenuTypeLocaleDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MenuTypeLocaleResponse {
    private final MenuTypeLocaleDto menuLocale;

    public MenuTypeLocaleResponse(MenuTypeLocaleDto menuLocale) {
        this.menuLocale = menuLocale;
    }
}
