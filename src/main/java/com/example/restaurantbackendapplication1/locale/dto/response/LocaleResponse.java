package com.example.restaurantbackendapplication1.locale.dto.response;

import com.example.restaurantbackendapplication1.locale.model.dto.LocaleDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LocaleResponse {
    private final LocaleDto locale;

    public LocaleResponse(LocaleDto locale) {
        this.locale = locale;
    }
}
