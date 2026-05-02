package com.example.restaurantbackendapplication1.dto.request.itemcategory;

import com.example.restaurantbackendapplication1.dto.request.itemcategorylocale.ItemCategoryLocaleRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemCategoryRequest {

    @NotNull
    private Long itemTypeId;

    @NotBlank
    @Size(max = 50)
    private String code;

    @NotNull
    private Integer sortOrder;

    private List<ItemCategoryLocaleRequest> locales;
}
