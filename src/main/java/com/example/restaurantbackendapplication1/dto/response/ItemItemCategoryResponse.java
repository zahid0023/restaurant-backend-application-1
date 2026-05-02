package com.example.restaurantbackendapplication1.dto.response;

import com.example.restaurantbackendapplication1.model.dto.ItemItemCategoryDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemItemCategoryResponse {
    private final ItemItemCategoryDto itemItemCategory;

    public ItemItemCategoryResponse(ItemItemCategoryDto itemItemCategory) {
        this.itemItemCategory = itemItemCategory;
    }
}
