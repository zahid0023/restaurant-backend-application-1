package com.example.restaurantbackendapplication1.inventory.dto.request.inventorylocation;

import com.example.restaurantbackendapplication1.inventory.dto.request.inventorylocationlocale.InventoryLocationLocaleRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InventoryLocationRequest {

    @NotBlank
    @Size(max = 50)
    private String code;

    @NotNull
    private Integer sortOrder;

    private List<InventoryLocationLocaleRequest> locales;
}
