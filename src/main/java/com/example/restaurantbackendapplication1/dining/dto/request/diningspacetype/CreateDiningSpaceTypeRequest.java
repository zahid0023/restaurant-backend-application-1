package com.example.restaurantbackendapplication1.dining.dto.request.diningspacetype;

import com.example.restaurantbackendapplication1.dining.dto.request.diningspacetypelocale.CreateDiningSpaceTypeLocaleRequest;
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
public class CreateDiningSpaceTypeRequest extends DiningSpaceTypeRequest {

    @NotBlank
    @Size(max = 50)
    private String code;

    private List<CreateDiningSpaceTypeLocaleRequest> locales;
}
