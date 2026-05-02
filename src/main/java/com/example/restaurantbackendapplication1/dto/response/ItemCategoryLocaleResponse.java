package com.example.restaurantbackendapplication1.dto.response;

import com.example.restaurantbackendapplication1.model.dto.ItemCategoryLocaleDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemCategoryLocaleResponse {
    private final ItemCategoryLocaleDto itemCategoryLocale;

    public ItemCategoryLocaleResponse(ItemCategoryLocaleDto itemCategoryLocale) {
        this.itemCategoryLocale = itemCategoryLocale;
    }
}
