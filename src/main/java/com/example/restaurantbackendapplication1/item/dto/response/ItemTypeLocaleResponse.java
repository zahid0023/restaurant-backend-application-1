package com.example.restaurantbackendapplication1.item.dto.response;

import com.example.restaurantbackendapplication1.item.model.dto.ItemTypeLocaleDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemTypeLocaleResponse {
    private final ItemTypeLocaleDto itemTypeLocale;

    public ItemTypeLocaleResponse(ItemTypeLocaleDto itemTypeLocale) {
        this.itemTypeLocale = itemTypeLocale;
    }
}
