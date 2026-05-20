package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface DiningSpaceSummary {
    Long getId();

    @Value("#{target.diningSpaceTypeEntity.id}")
    Long getDiningSpaceTypeId();

    @Value("#{target.floorEntity != null ? target.floorEntity.id : null}")
    Long getFloorId();

    String getCode();
    Integer getSortOrder();
    Integer getCapacity();
    Boolean getIsBookable();

    @Value("#{target.diningSpaceLocaleEntities}")
    List<LocaleSummary> getLocales();

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    interface LocaleSummary {
        Long getId();

        @Value("#{target.localeEntity.id}")
        Long getLocaleId();

        String getName();
        String getDescription();
        Integer getSortOrder();
    }
}
