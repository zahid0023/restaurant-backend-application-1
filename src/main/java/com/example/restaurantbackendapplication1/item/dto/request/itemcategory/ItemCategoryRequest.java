package com.example.restaurantbackendapplication1.item.dto.request.itemcategory;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemCategoryRequest {
    @NotNull
    private Integer sortOrder;
}
