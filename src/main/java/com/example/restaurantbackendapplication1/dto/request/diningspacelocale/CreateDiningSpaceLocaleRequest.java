package com.example.restaurantbackendapplication1.dto.request.diningspacelocale;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateDiningSpaceLocaleRequest extends DiningSpaceLocaleRequest {

    @NotNull
    private Long localeId;
}
