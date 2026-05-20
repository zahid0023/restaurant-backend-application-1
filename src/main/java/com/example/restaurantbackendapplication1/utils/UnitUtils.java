package com.example.restaurantbackendapplication1.utils;

import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.service.UnitService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UnitUtils {

    public static <T> Map<Long, UnitEntity> resolveUnitMap(
            List<T> requests,
            Function<T, Long> unitIdExtractor,
            UnitService unitService) {
        if (requests == null || requests.isEmpty()) {
            return Collections.emptyMap();
        }
        Set<Long> unitIds = requests.stream()
                .map(unitIdExtractor)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (unitIds.isEmpty()) {
            return Collections.emptyMap();
        }
        return unitService.getAll(unitIds).stream()
                .collect(Collectors.toMap(UnitEntity::getId, u -> u));
    }
}
