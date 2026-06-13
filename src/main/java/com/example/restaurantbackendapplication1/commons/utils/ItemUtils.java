package com.example.restaurantbackendapplication1.commons.utils;

import com.example.restaurantbackendapplication1.item.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.item.service.ItemService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ItemUtils {

    public static <T> Map<Long, ItemEntity> resolveItemMap(
            List<T> requests,
            Function<T, Long> itemIdExtractor,
            ItemService itemService) {
        if (requests == null || requests.isEmpty()) {
            return Collections.emptyMap();
        }
        Set<Long> itemIds = requests.stream()
                .map(itemIdExtractor)
                .collect(Collectors.toSet());
        return itemService.getAll(itemIds).stream()
                .collect(Collectors.toMap(ItemEntity::getId, i -> i));
    }
}
