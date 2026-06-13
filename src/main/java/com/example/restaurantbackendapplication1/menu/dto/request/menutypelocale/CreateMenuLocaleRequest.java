package com.example.restaurantbackendapplication1.menu.dto.request.menutypelocale;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateMenuLocaleRequest extends MenuLocaleRequest {

    @NotNull
    private Long localeId;
}
