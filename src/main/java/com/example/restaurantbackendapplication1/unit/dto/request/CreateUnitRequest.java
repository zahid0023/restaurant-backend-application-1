package com.example.restaurantbackendapplication1.unit.dto.request;

import com.example.restaurantbackendapplication1.unit.dto.request.unitlocale.CreateUnitLocaleRequest;
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
public class CreateUnitRequest extends UnitRequest {

    @NotBlank
    @Size(max = 20)
    private String code;

    private List<CreateUnitLocaleRequest> locales;
}
