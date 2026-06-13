package com.example.restaurantbackendapplication1.inventory.dto.response;

import com.example.restaurantbackendapplication1.inventory.model.dto.InventoryLocationLocaleDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InventoryLocationLocaleResponse {
    private final InventoryLocationLocaleDto inventoryLocationLocale;

    public InventoryLocationLocaleResponse(InventoryLocationLocaleDto inventoryLocationLocale) {
        this.inventoryLocationLocale = inventoryLocationLocale;
    }
}
