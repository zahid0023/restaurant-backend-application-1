package com.example.restaurantbackendapplication1.dining.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dining.dto.request.diningspace.CreateDiningSpaceRequest;
import com.example.restaurantbackendapplication1.dining.dto.request.diningspace.UpdateDiningSpaceRequest;
import com.example.restaurantbackendapplication1.dining.dto.response.DiningSpaceResponse;
import com.example.restaurantbackendapplication1.dining.model.dto.DiningSpaceDto;
import com.example.restaurantbackendapplication1.dining.model.entity.DiningSpaceEntity;
import com.example.restaurantbackendapplication1.dining.model.entity.DiningSpaceTypeEntity;
import com.example.restaurantbackendapplication1.dining.model.entity.FloorEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.dining.model.enums.DiningSpaceSortField;
import com.example.restaurantbackendapplication1.dining.model.mapper.DiningSpaceMapper;
import com.example.restaurantbackendapplication1.dining.model.projection.DiningSpaceSummary;
import com.example.restaurantbackendapplication1.dining.repository.DiningSpaceRepository;
import com.example.restaurantbackendapplication1.dining.service.DiningSpaceService;
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
public class DiningSpaceServiceImpl implements DiningSpaceService {

    private static final Set<String> ALLOWED_SORT_FIELDS = DiningSpaceSortField.allowedFields();

    private final DiningSpaceRepository diningSpaceRepository;

    public DiningSpaceServiceImpl(DiningSpaceRepository diningSpaceRepository) {
        this.diningSpaceRepository = diningSpaceRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateDiningSpaceRequest request,
                                  DiningSpaceTypeEntity diningSpaceTypeEntity,
                                  FloorEntity floorEntity,
                                  Map<Long, LocaleEntity> localeEntityMap) {
        DiningSpaceEntity entity = DiningSpaceMapper.create(request, diningSpaceTypeEntity, floorEntity, localeEntityMap);
        diningSpaceRepository.save(entity);
        log.info("DiningSpace created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public DiningSpaceEntity getEntityById(Long id) {
        return diningSpaceRepository.findByIdAndIsActiveAndIsDeleted(id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("DiningSpace not found with id: " + id));
    }

    @Override
    public DiningSpaceResponse getById(Long id) {
        DiningSpaceEntity entity = getEntityById(id);
        DiningSpaceDto dto = DiningSpaceMapper.toDto(entity);
        return new DiningSpaceResponse(dto);
    }

    @Override
    public PaginatedResponse<DiningSpaceSummary> getAll(PaginatedRequest request) {
        Page<@NonNull DiningSpaceSummary> page = diningSpaceRepository.findAllByIsActiveAndIsDeleted(
                true, false, request.toPageable(ALLOWED_SORT_FIELDS)
        );
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(DiningSpaceEntity entity,
                                  UpdateDiningSpaceRequest request) {
        DiningSpaceMapper.update(entity, request);
        diningSpaceRepository.save(entity);
        log.info("DiningSpace updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(Long id) {
        DiningSpaceEntity entity = getEntityById(id);
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        diningSpaceRepository.save(entity);
        log.info("DiningSpace soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
