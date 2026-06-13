package com.example.restaurantbackendapplication1.item.dto.response;

import com.example.restaurantbackendapplication1.item.model.dto.ItemTypeDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemTypeResponse {
    private final ItemTypeDto itemType;

    public ItemTypeResponse(ItemTypeDto itemType) {
        this.itemType = itemType;
    }
}
