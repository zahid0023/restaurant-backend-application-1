package com.example.restaurantbackendapplication1.dining.dto.request.diningspacetypelocale;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateDiningSpaceTypeLocaleRequest extends DiningSpaceTypeLocaleRequest {

    @NotNull
    private Long localeId;
}
