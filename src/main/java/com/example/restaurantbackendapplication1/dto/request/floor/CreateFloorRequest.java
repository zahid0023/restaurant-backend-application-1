package com.example.restaurantbackendapplication1.dto.request.floor;

import com.example.restaurantbackendapplication1.dto.request.floor.floorlocale.CreateFloorLocaleRequest;
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
public class CreateFloorRequest extends FloorRequest {

    @NotBlank
    @Size(max = 50)
    private String code;

    private List<CreateFloorLocaleRequest> locales;
}
