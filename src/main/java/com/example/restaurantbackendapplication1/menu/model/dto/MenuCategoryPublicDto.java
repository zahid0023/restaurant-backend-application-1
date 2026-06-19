package com.example.restaurantbackendapplication1.menu.model.dto;

import com.example.restaurantbackendapplication1.dish.model.dto.FeaturedDishDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MenuCategoryPublicDto {
    private Long id;
    private List<LocaleSummary> locales;
    private List<FeaturedDishDto> dishes;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class LocaleSummary {
        private Long localeId;
        private String name;
        private String description;
    }
}
