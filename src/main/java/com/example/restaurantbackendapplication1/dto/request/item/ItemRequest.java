package com.example.restaurantbackendapplication1.dto.request.item;

import com.example.restaurantbackendapplication1.dto.request.itemlocale.ItemLocaleRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemRequest {
    private String code;

    @NotNull
    private Long unitId;

    @NotNull
    private Integer sortOrder;

    private List<ItemLocaleRequest> locales;
}
