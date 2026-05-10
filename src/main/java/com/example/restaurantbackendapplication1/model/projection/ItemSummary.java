package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface ItemSummary {
    Long getId();

    @Value("#{target.itemTypeEntity.id}")
    Long getItemTypeId();

    @Value("#{target.unitEntity.id}")
    Long getUnitId();

    Integer getSortOrder();
}
