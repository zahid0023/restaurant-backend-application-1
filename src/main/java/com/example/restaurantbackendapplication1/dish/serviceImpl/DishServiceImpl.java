package com.example.restaurantbackendapplication1.dish.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dish.dto.request.CreateDishRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.UpdateDishRequest;
import com.example.restaurantbackendapplication1.dish.dto.response.DishResponse;
import com.example.restaurantbackendapplication1.dish.model.dto.DishDto;
import com.example.restaurantbackendapplication1.dish.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.dish.model.enums.DishSortField;
import com.example.restaurantbackendapplication1.dish.model.mapper.DishMapper;
import com.example.restaurantbackendapplication1.dish.model.projection.DishSummary;
import com.example.restaurantbackendapplication1.dish.repository.DishRepository;
import com.example.restaurantbackendapplication1.dish.service.DishService;
import com.example.restaurantbackendapplication1.commons.utils.Pagination;
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
    public SuccessResponse create(CreateDishRequest request, Map<Long, LocaleEntity> localeEntityMap) {
        DishEntity entity = DishMapper.create(request, localeEntityMap);
        dishRepository.save(entity);
        log.info("Dish created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public DishEntity getEntityById(Long id) {
        return dishRepository.findByIdAndIsActiveAndIsDeleted(id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("Dish not found with id: " + id));
    }

    @Override
    public DishResponse getById(Long id) {
        DishEntity entity = getEntityById(id);
        DishDto dto = DishMapper.toDto(entity);
        return new DishResponse(dto);
    }

    @Override
    public PaginatedResponse<DishSummary> getAll(PaginatedRequest request) {
        Page<@NonNull DishSummary> page = dishRepository
                .findAllByIsActiveAndIsDeleted(true, false, request.toPageable(ALLOWED_SORT_FIELDS));
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
    public SuccessResponse delete(Long id) {
        DishEntity entity = getEntityById(id);
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        dishRepository.save(entity);
        log.info("Dish soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
