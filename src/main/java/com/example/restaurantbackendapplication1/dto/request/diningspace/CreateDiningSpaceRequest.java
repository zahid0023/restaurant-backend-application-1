package com.example.restaurantbackendapplication1.dto.request.diningspace;

import com.example.restaurantbackendapplication1.dto.request.diningspacelocale.CreateDiningSpaceLocaleRequest;
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
public class CreateDiningSpaceRequest extends DiningSpaceRequest {
    @NotNull
    private Long diningSpaceTypeId;

    private Long floorId;

    @NotBlank
    @Size(max = 50)
    private String code;

    private List<CreateDiningSpaceLocaleRequest> locales;
}
