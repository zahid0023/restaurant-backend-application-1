package com.example.restaurantbackendapplication1.menu.dto.request.menutype;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MenuTypeRequest {

    @NotNull
    private Integer sortOrder;
}
