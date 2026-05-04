package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.dishvariantlocale.CreateDishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.dishvariantlocale.UpdateDishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.DishVariantLocaleResponse;
import com.example.restaurantbackendapplication1.model.dto.DishVariantLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.model.entity.DishVariantLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.enums.DishVariantLocaleSortField;
import com.example.restaurantbackendapplication1.model.mapper.DishVariantLocaleMapper;
import com.example.restaurantbackendapplication1.model.projection.DishVariantLocaleSummary;
import com.example.restaurantbackendapplication1.repository.DishVariantLocaleRepository;
import com.example.restaurantbackendapplication1.service.DishVariantLocaleService;
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
public class DishVariantLocaleServiceImpl implements DishVariantLocaleService {

    private static final Set<String> ALLOWED_SORT_FIELDS = DishVariantLocaleSortField.allowedFields();

    private final DishVariantLocaleRepository dishVariantLocaleRepository;

    public DishVariantLocaleServiceImpl(DishVariantLocaleRepository dishVariantLocaleRepository) {
        this.dishVariantLocaleRepository = dishVariantLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(DishVariantEntity dishVariantEntity,
                                  LocaleEntity localeEntity,
                                  CreateDishVariantLocaleRequest request) {
        DishVariantLocaleEntity entity = DishVariantLocaleMapper.fromRequest(request, dishVariantEntity, localeEntity);
        dishVariantLocaleRepository.save(entity);
        log.info("DishVariantLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public DishVariantLocaleEntity getEntityById(Long dishVariantId, Long id) {
        return dishVariantLocaleRepository.findByDishVariantEntity_IdAndIdAndIsActiveAndIsDeleted(dishVariantId, id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("DishVariant locale not found with id: " + id));
    }

    @Override
    public DishVariantLocaleResponse getById(Long dishVariantId, Long id) {
        DishVariantLocaleEntity entity = getEntityById(dishVariantId, id);
        DishVariantLocaleDto dto = DishVariantLocaleMapper.toDto(entity);
        return new DishVariantLocaleResponse(dto);
    }

    @Override
    public PaginatedResponse<DishVariantLocaleSummary> getAll(Long dishVariantId, PaginatedRequest request) {
        Page<@NonNull DishVariantLocaleSummary> page = dishVariantLocaleRepository
                .findAllByDishVariantEntity_IdAndIsActiveAndIsDeleted(dishVariantId, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(DishVariantLocaleEntity entity,
                                  LocaleEntity localeEntity,
                                  UpdateDishVariantLocaleRequest request) {
        DishVariantLocaleMapper.update(entity, request, localeEntity);
        dishVariantLocaleRepository.save(entity);
        log.info("DishVariantLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(DishVariantLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        dishVariantLocaleRepository.save(entity);
        log.info("DishVariantLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
