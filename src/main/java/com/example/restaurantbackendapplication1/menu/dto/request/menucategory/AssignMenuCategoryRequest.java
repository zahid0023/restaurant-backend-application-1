package com.example.restaurantbackendapplication1.menu.dto.request.menucategory;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AssignMenuCategoryRequest {

    @NotNull
    private Long menuId;

    @NotNull
    private Long menuCategoryId;
}
