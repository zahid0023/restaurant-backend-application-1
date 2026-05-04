package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.dishvariant.CreateDishVariantRequest;
import com.example.restaurantbackendapplication1.dto.request.dishvariant.UpdateDishVariantRequest;
import com.example.restaurantbackendapplication1.dto.response.DishVariantResponse;
import com.example.restaurantbackendapplication1.model.dto.DishVariantDto;
import com.example.restaurantbackendapplication1.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.enums.DishVariantSortField;
import com.example.restaurantbackendapplication1.model.mapper.DishVariantMapper;
import com.example.restaurantbackendapplication1.model.projection.DishVariantSummary;
import com.example.restaurantbackendapplication1.repository.DishVariantRepository;
import com.example.restaurantbackendapplication1.service.DishVariantService;
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
public class DishVariantServiceImpl implements DishVariantService {

    private static final Set<String> ALLOWED_SORT_FIELDS = DishVariantSortField.allowedFields();

    private final DishVariantRepository dishVariantRepository;

    public DishVariantServiceImpl(DishVariantRepository dishVariantRepository) {
        this.dishVariantRepository = dishVariantRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateDishVariantRequest request,
                                  DishEntity dishEntity,
                                  Map<Long, LocaleEntity> localeEntityMap) {
        DishVariantEntity entity = DishVariantMapper.fromRequest(request, dishEntity, localeEntityMap);
        dishVariantRepository.save(entity);
        log.info("DishVariant created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public DishVariantEntity getEntityById(Long dishId, Long id) {
        return dishVariantRepository.findByDishEntity_IdAndIdAndIsActiveAndIsDeleted(dishId, id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("DishVariant not found with id: " + id));
    }

    @Override
    public DishVariantResponse getById(Long dishId, Long id) {
        DishVariantEntity entity = getEntityById(dishId, id);
        DishVariantDto dto = DishVariantMapper.toDto(entity);
        return new DishVariantResponse(dto);
    }

    @Override
    public PaginatedResponse<DishVariantSummary> getAll(Long dishId, PaginatedRequest request) {
        Page<@NonNull DishVariantSummary> page = dishVariantRepository
                .findAllByDishEntity_IdAndIsActiveAndIsDeleted(dishId, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(DishVariantEntity entity, UpdateDishVariantRequest request) {
        DishVariantMapper.update(entity, request);
        dishVariantRepository.save(entity);
        log.info("DishVariant updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(Long dishId, Long id) {
        DishVariantEntity entity = getEntityById(dishId, id);
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        dishVariantRepository.save(entity);
        log.info("DishVariant soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
