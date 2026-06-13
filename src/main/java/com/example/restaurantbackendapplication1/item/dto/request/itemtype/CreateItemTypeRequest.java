package com.example.restaurantbackendapplication1.item.dto.request.itemtype;

import com.example.restaurantbackendapplication1.item.dto.request.itemtypelocale.CreateItemTypeLocaleRequest;
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
public class CreateItemTypeRequest extends ItemTypeRequest {

    @NotBlank
    @Size(max = 50)
    private String code;

    private List<CreateItemTypeLocaleRequest> locales;
}
