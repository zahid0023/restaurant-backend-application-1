package com.example.restaurantbackendapplication1.item.dto.request.itemcategory;

import com.example.restaurantbackendapplication1.item.dto.request.itemcategorylocale.CreateItemCategoryLocaleRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateItemCategoryRequest extends ItemCategoryRequest {
    @NotBlank
    @Size(max = 50)
    private String code;

    private Long parentId;

    private List<CreateItemCategoryLocaleRequest> locales;
}
