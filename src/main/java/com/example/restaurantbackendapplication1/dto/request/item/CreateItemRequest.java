package com.example.restaurantbackendapplication1.dto.request.item;

import com.example.restaurantbackendapplication1.dto.request.itemlocale.CreateItemLocaleRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateItemRequest extends ItemRequest {
    @NotNull
    private Long itemTypeId;

    @NotNull
    private Long unitTypeId;

    @NotBlank
    @Size(max = 20)
    private String code;

    private List<CreateItemLocaleRequest> locales;
}
