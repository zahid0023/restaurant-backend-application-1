package com.example.restaurantbackendapplication1.dto.response;

import com.example.restaurantbackendapplication1.model.dto.ItemCategoryDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemCategoryResponse {
    private final ItemCategoryDto itemCategory;

    public ItemCategoryResponse(ItemCategoryDto itemCategory) {
        this.itemCategory = itemCategory;
    }
}
