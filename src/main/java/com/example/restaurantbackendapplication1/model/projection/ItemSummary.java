package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;

public interface ItemSummary {
    Long getId();

    @Value("#{target.itemTypeEntity.id}")
    Long getItemTypeId();

    @Value("#{target.unitEntity.id}")
    Long getUnitId();

    Integer getSortOrder();
}
