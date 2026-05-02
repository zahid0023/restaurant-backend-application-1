package com.example.restaurantbackendapplication1.dto.response;

import com.example.restaurantbackendapplication1.model.dto.MenuSectionLocaleDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MenuSectionLocaleResponse {
    private final MenuSectionLocaleDto menuSectionLocale;

    public MenuSectionLocaleResponse(MenuSectionLocaleDto menuSectionLocale) {
        this.menuSectionLocale = menuSectionLocale;
    }
}
