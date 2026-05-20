package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.dish.CreateDishRequest;
import com.example.restaurantbackendapplication1.dto.request.dish.UpdateDishRequest;
import com.example.restaurantbackendapplication1.dto.response.DishResponse;
import com.example.restaurantbackendapplication1.model.dto.DishDto;
import com.example.restaurantbackendapplication1.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.model.enums.DishSortField;
import com.example.restaurantbackendapplication1.model.mapper.DishMapper;
import com.example.restaurantbackendapplication1.model.projection.DishSummary;
import com.example.restaurantbackendapplication1.repository.DishRepository;
import com.example.restaurantbackendapplication1.service.DishService;
import com.example.restaurantbackendapplication1.utils.Pagination;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    private static final Set<String> ALLOWED_SORT_FIELDS = DishSortField.allowedFields();

    private final DishRepository dishRepository;

    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateDishRequest request,
                                  MenuCategoryEntity menuCategoryEntity,
                                  Map<Long, LocaleEntity> localeEntityMap,
                                  Map<Long, ItemEntity> itemEntityMap,
                                  Map<Long, UnitEntity> unitEntityMap) {
        DishEntity entity = DishMapper.create(request, menuCategoryEntity, localeEntityMap, itemEntityMap, unitEntityMap);
        dishRepository.save(entity);
        log.info("Dish created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public DishEntity getEntityById(Long menuCategoryId, Long id) {
        return dishRepository.findByMenuCategoryEntity_IdAndIdAndIsActiveAndIsDeleted(menuCategoryId, id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("Dish not found with id: " + id));
    }

    @Override
    public DishResponse getById(Long menuCategoryId, Long id) {
        DishEntity entity = getEntityById(menuCategoryId, id);
        DishDto dto = DishMapper.toDto(entity);
        return new DishResponse(dto);
    }

    @Override
    public PaginatedResponse<DishSummary> getAll(Long menuCategoryId, PaginatedRequest request) {
        Page<@NonNull DishSummary> page = dishRepository
                .findAllByMenuCategoryEntity_IdAndIsActiveAndIsDeleted(menuCategoryId, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(DishEntity entity, UpdateDishRequest request) {
        DishMapper.update(entity, request);
        dishRepository.save(entity);
        log.info("Dish updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(Long menuCategoryId, Long id) {
        DishEntity entity = getEntityById(menuCategoryId, id);
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        dishRepository.save(entity);
        log.info("Dish soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
