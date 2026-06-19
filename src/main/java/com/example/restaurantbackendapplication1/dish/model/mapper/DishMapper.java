package com.example.restaurantbackendapplication1.dish.model.mapper;

import com.example.restaurantbackendapplication1.dish.dto.request.CreateDishRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.DishRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.UpdateDishRequest;
import com.example.restaurantbackendapplication1.dish.model.dto.DishDto;
import com.example.restaurantbackendapplication1.dish.model.dto.DishLocaleDto;
import com.example.restaurantbackendapplication1.dish.model.dto.DishVariantDto;
import com.example.restaurantbackendapplication1.dish.model.dto.FeaturedDishDto;
import com.example.restaurantbackendapplication1.dish.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.dish.model.entity.DishLocaleEntity;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class DishMapper {

    public DishEntity create(CreateDishRequest request,
                             Map<Long, LocaleEntity> localeEntityMap) {
        DishEntity entity = new DishEntity();
        entity.setCode(request.getCode());
        applyCommonFields(entity, request);

        if (request.getLocales() != null) {
            Set<DishLocaleEntity> localeEntities = request.getLocales().stream()
                    .map(lr -> DishLocaleMapper.create(lr, entity, localeEntityMap.get(lr.getLocaleId())))
                    .collect(Collectors.toSet());
            entity.setDishesLocaleEntities(localeEntities);
        }

        return entity;
    }

    public void update(DishEntity entity, UpdateDishRequest request) {
        applyCommonFields(entity, request);
    }

    private void applyCommonFields(DishEntity entity, DishRequest request) {
        entity.setSortOrder(request.getSortOrder());
    }

    public FeaturedDishDto toFeaturedDto(DishEntity entity) {
        List<FeaturedDishDto.LocaleSummary> locales = entity.getDishesLocaleEntities().stream()
                .map(l -> FeaturedDishDto.LocaleSummary.builder()
                        .localeId(l.getLocaleEntity().getId())
                        .name(l.getName())
                        .description(l.getDescription())
                        .build())
                .toList();
        var cheapest = entity.getDishVariantEntities().stream()
                .filter(v -> Boolean.TRUE.equals(v.getIsActive()) && Boolean.FALSE.equals(v.getIsDeleted()))
                .min(Comparator.comparing(DishVariantEntity::getPrice))
                .orElse(null);
        return FeaturedDishDto.builder()
                .id(entity.getId())
                .locales(locales)
                .cheapestVariantId(cheapest != null ? cheapest.getId() : null)
                .price(cheapest != null ? cheapest.getPrice() : null)
                .img("https://res.cloudinary.com/dg9kza9o9/image/upload/v1781891538/smoothie_z55k5f.jpg")
                .imgAlt("smoothie")
                .build();
    }

    public DishDto toDto(DishEntity entity, Boolean includeVariants) {
        List<DishLocaleDto> locales = entity.getDishesLocaleEntities().stream()
                .map(DishLocaleMapper::toDto)
                .toList();
        List<DishVariantDto> variants = includeVariants ?
                entity.getDishVariantEntities().stream()
                        .filter(v -> Boolean.TRUE.equals(v.getIsActive()) && Boolean.FALSE.equals(v.getIsDeleted()))
                        .map(v -> DishVariantMapper.toDto(v, false, false))
                        .toList()
                : null;
        return DishDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .sortOrder(entity.getSortOrder())
                .isFeatured(entity.getIsFeatured())
                .locales(locales)
                .variants(variants)
                .build();
    }
}
