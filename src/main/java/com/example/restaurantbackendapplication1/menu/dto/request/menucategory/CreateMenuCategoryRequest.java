package com.example.restaurantbackendapplication1.menu.dto.request.menucategory;

import com.example.restaurantbackendapplication1.menu.dto.request.menucategorylocale.CreateMenuCategoryLocaleRequest;
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
public class CreateMenuCategoryRequest extends MenuCategoryRequest {
    private Long menuTypeId;

    @NotBlank
    @Size(max = 50)
    private String code;

    private List<CreateMenuCategoryLocaleRequest> locales;
}
