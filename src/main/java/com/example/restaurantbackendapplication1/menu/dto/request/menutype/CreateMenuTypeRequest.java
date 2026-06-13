package com.example.restaurantbackendapplication1.menu.dto.request.menutype;

import com.example.restaurantbackendapplication1.menu.dto.request.menutypelocale.CreateMenuLocaleRequest;
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
public class CreateMenuTypeRequest extends MenuTypeRequest {

    @NotBlank
    @Size(max = 50)
    private String code;

    private List<CreateMenuLocaleRequest> locales;
}
