package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface UnitSummary {
    Long getId();

    @Value("#{target.unitTypeEntity.id}")
    Long getUnitTypeId();

    String getCode();
    Boolean getIsBase();
    Integer getSortOrder();
}
