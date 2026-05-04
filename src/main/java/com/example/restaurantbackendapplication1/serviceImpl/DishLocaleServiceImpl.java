package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.dishlocale.CreateDishLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.dishlocale.UpdateDishLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.DishLocaleResponse;
import com.example.restaurantbackendapplication1.model.dto.DishLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.model.entity.DishesLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.enums.DishLocaleSortField;
import com.example.restaurantbackendapplication1.model.mapper.DishLocaleMapper;
import com.example.restaurantbackendapplication1.model.projection.DishLocaleSummary;
import com.example.restaurantbackendapplication1.repository.DishLocaleRepository;
import com.example.restaurantbackendapplication1.service.DishLocaleService;
import com.example.restaurantbackendapplication1.utils.Pagination;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Slf4j
public class DishLocaleServiceImpl implements DishLocaleService {

    private static final Set<String> ALLOWED_SORT_FIELDS = DishLocaleSortField.allowedFields();

    private final DishLocaleRepository dishLocaleRepository;

    public DishLocaleServiceImpl(DishLocaleRepository dishLocaleRepository) {
        this.dishLocaleRepository = dishLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(DishEntity dishEntity,
                                  LocaleEntity localeEntity,
                                  CreateDishLocaleRequest request) {
        DishesLocaleEntity entity = DishLocaleMapper.fromRequest(request, dishEntity, localeEntity);
        dishLocaleRepository.save(entity);
        log.info("DishLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public DishesLocaleEntity getEntityById(Long dishId, Long id) {
        return dishLocaleRepository.findByDishEntity_IdAndIdAndIsActiveAndIsDeleted(dishId, id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("Dish locale not found with id: " + id));
    }

    @Override
    public DishLocaleResponse getById(Long dishId, Long id) {
        DishesLocaleEntity entity = getEntityById(dishId, id);
        DishLocaleDto dto = DishLocaleMapper.toDto(entity);
        return new DishLocaleResponse(dto);
    }

    @Override
    public PaginatedResponse<DishLocaleSummary> getAll(Long dishId, PaginatedRequest request) {
        Page<@NonNull DishLocaleSummary> page = dishLocaleRepository
                .findAllByDishEntity_IdAndIsActiveAndIsDeleted(dishId, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(DishesLocaleEntity entity,
                                  LocaleEntity localeEntity,
                                  UpdateDishLocaleRequest request) {
        DishLocaleMapper.update(entity, request, localeEntity);
        dishLocaleRepository.save(entity);
        log.info("DishLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(DishesLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        dishLocaleRepository.save(entity);
        log.info("DishLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
