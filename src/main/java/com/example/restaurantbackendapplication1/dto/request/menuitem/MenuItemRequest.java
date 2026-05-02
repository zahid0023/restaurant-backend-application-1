package com.example.restaurantbackendapplication1.dto.request.menuitem;

import com.example.restaurantbackendapplication1.dto.request.menuitemlocale.MenuItemLocaleRequest;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MenuItemRequest {

    @NotBlank
    @Size(max = 50)
    private String code;

    @NotNull
    private Integer sortOrder;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 16, fraction = 2)
    private BigDecimal price;

    private Boolean isAvailable;

    private Boolean isFeatured;

    private Boolean isVeg;

    private List<MenuItemLocaleRequest> locales;
}
