package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.diningspacelocale.CreateDiningSpaceLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacelocale.UpdateDiningSpaceLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.DiningSpaceLocaleResponse;
import com.example.restaurantbackendapplication1.model.dto.DiningSpaceLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceEntity;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.enums.DiningSpaceLocaleSortField;
import com.example.restaurantbackendapplication1.model.mapper.DiningSpaceLocaleMapper;
import com.example.restaurantbackendapplication1.model.projection.DiningSpaceLocaleSummary;
import com.example.restaurantbackendapplication1.repository.DiningSpaceLocaleRepository;
import com.example.restaurantbackendapplication1.service.DiningSpaceLocaleService;
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
public class DiningSpaceLocaleServiceImpl implements DiningSpaceLocaleService {

    private static final Set<String> ALLOWED_SORT_FIELDS = DiningSpaceLocaleSortField.allowedFields();

    private final DiningSpaceLocaleRepository diningSpaceLocaleRepository;

    public DiningSpaceLocaleServiceImpl(DiningSpaceLocaleRepository diningSpaceLocaleRepository) {
        this.diningSpaceLocaleRepository = diningSpaceLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(DiningSpaceEntity diningSpace, LocaleEntity locale,
                                  CreateDiningSpaceLocaleRequest request) {
        DiningSpaceLocaleEntity entity = DiningSpaceLocaleMapper.fromRequest(request, diningSpace, locale);
        diningSpaceLocaleRepository.save(entity);
        log.info("DiningSpaceLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public DiningSpaceLocaleEntity getEntityById(Long id, DiningSpaceEntity diningSpace) {
        return diningSpaceLocaleRepository
                .findByIdAndDiningSpaceEntityAndIsActiveAndIsDeleted(id, diningSpace, true, false)
                .orElseThrow(() -> new EntityNotFoundException("DiningSpaceLocale not found with id: " + id));
    }

    @Override
    public DiningSpaceLocaleResponse getById(Long id, DiningSpaceEntity diningSpace) {
        DiningSpaceLocaleEntity entity = getEntityById(id, diningSpace);
        DiningSpaceLocaleDto dto = DiningSpaceLocaleMapper.toDto(entity);
        return new DiningSpaceLocaleResponse(dto);
    }

    @Override
    public PaginatedResponse<DiningSpaceLocaleSummary> getAll(DiningSpaceEntity diningSpace, PaginatedRequest request) {
        Page<@NonNull DiningSpaceLocaleSummary> page = diningSpaceLocaleRepository
                .findAllByDiningSpaceEntityAndIsActiveAndIsDeleted(
                        diningSpace, true, false, request.toPageable(ALLOWED_SORT_FIELDS)
                );
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(DiningSpaceLocaleEntity entity, LocaleEntity locale,
                                  UpdateDiningSpaceLocaleRequest request) {
        DiningSpaceLocaleMapper.update(entity, request, locale);
        diningSpaceLocaleRepository.save(entity);
        log.info("DiningSpaceLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(DiningSpaceLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        diningSpaceLocaleRepository.save(entity);
        log.info("DiningSpaceLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
