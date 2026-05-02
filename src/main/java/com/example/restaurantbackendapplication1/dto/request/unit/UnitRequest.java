package com.example.restaurantbackendapplication1.dto.request.unit;

import com.example.restaurantbackendapplication1.dto.request.unitlocale.UnitLocaleRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UnitRequest {

    @NotNull
    private Long unitTypeId;

    @NotBlank
    @Size(max = 20)
    private String code;

    @NotNull
    private Boolean isBase;

    @NotNull
    private Integer sortOrder;

    private List<UnitLocaleRequest> locales;
}
