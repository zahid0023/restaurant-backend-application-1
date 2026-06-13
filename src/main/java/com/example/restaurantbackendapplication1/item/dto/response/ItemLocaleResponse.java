package com.example.restaurantbackendapplication1.item.dto.response;

import com.example.restaurantbackendapplication1.item.model.dto.ItemLocaleDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemLocaleResponse {
    private final ItemLocaleDto itemLocale;

    public ItemLocaleResponse(ItemLocaleDto itemLocale) {
        this.itemLocale = itemLocale;
    }
}
