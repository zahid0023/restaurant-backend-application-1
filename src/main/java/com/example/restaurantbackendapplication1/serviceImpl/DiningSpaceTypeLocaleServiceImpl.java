package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.diningspacetypelocale.CreateDiningSpaceTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacetypelocale.UpdateDiningSpaceTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.DiningSpaceTypeLocaleResponse;
import com.example.restaurantbackendapplication1.model.dto.DiningSpaceTypeLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.enums.DiningSpaceTypeLocaleSortField;
import com.example.restaurantbackendapplication1.model.mapper.DiningSpaceTypeLocaleMapper;
import com.example.restaurantbackendapplication1.model.projection.DiningSpaceTypeLocaleSummary;
import com.example.restaurantbackendapplication1.repository.DiningSpaceTypeLocaleRepository;
import com.example.restaurantbackendapplication1.service.DiningSpaceTypeLocaleService;
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
public class DiningSpaceTypeLocaleServiceImpl implements DiningSpaceTypeLocaleService {

    private static final Set<String> ALLOWED_SORT_FIELDS = DiningSpaceTypeLocaleSortField.allowedFields();

    private final DiningSpaceTypeLocaleRepository diningSpaceTypeLocaleRepository;

    public DiningSpaceTypeLocaleServiceImpl(DiningSpaceTypeLocaleRepository diningSpaceTypeLocaleRepository) {
        this.diningSpaceTypeLocaleRepository = diningSpaceTypeLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(DiningSpaceTypeEntity diningSpaceType, LocaleEntity locale,
                                  CreateDiningSpaceTypeLocaleRequest request) {
        DiningSpaceTypeLocaleEntity entity = DiningSpaceTypeLocaleMapper.fromRequest(request, diningSpaceType, locale);
        diningSpaceTypeLocaleRepository.save(entity);
        log.info("DiningSpaceTypeLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public DiningSpaceTypeLocaleResponse getById(Long id, DiningSpaceTypeEntity diningSpaceType) {
        DiningSpaceTypeLocaleEntity entity = getEntityById(id, diningSpaceType);
        DiningSpaceTypeLocaleDto dto = DiningSpaceTypeLocaleMapper.toDto(entity);
        return new DiningSpaceTypeLocaleResponse(dto);
    }

    @Override
    public PaginatedResponse<DiningSpaceTypeLocaleSummary> getAll(DiningSpaceTypeEntity diningSpaceType,
                                                                  PaginatedRequest request) {
        Page<@NonNull DiningSpaceTypeLocaleSummary> page = diningSpaceTypeLocaleRepository
                .findAllByDiningSpaceTypeEntityAndIsActiveAndIsDeleted(
                        diningSpaceType, true, false, request.toPageable(ALLOWED_SORT_FIELDS)
                );
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(DiningSpaceTypeLocaleEntity entity, LocaleEntity locale,
                                  UpdateDiningSpaceTypeLocaleRequest request) {
        DiningSpaceTypeLocaleMapper.update(entity, request, locale);
        diningSpaceTypeLocaleRepository.save(entity);
        log.info("DiningSpaceTypeLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(DiningSpaceTypeLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        diningSpaceTypeLocaleRepository.save(entity);
        log.info("DiningSpaceTypeLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public DiningSpaceTypeLocaleEntity getEntityById(Long id, DiningSpaceTypeEntity diningSpaceType) {
        return diningSpaceTypeLocaleRepository
                .findByIdAndDiningSpaceTypeEntityAndIsActiveAndIsDeleted(id, diningSpaceType, true, false)
                .orElseThrow(() -> new EntityNotFoundException("DiningSpaceTypeLocale not found with id: " + id));
    }
}
