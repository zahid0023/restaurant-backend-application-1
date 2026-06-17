package com.example.restaurantbackendapplication1.imagehosting.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.commons.utils.Pagination;
import com.example.restaurantbackendapplication1.imagehosting.dto.request.CreateRestaurantImageHostingConfigRequest;
import com.example.restaurantbackendapplication1.imagehosting.dto.request.UpdateRestaurantImageHostingConfigRequest;
import com.example.restaurantbackendapplication1.imagehosting.dto.response.RestaurantImageHostingConfigResponse;
import com.example.restaurantbackendapplication1.imagehosting.enums.RestaurantImageHostingConfigSortField;
import com.example.restaurantbackendapplication1.imagehosting.model.dto.RestaurantImageHostingConfigDto;
import com.example.restaurantbackendapplication1.imagehosting.model.entity.RestaurantImageHostingConfigEntity;
import com.example.restaurantbackendapplication1.imagehosting.model.mapper.RestaurantImageHostingConfigMapper;
import com.example.restaurantbackendapplication1.imagehosting.model.projection.RestaurantImageHostingConfigSummary;
import com.example.restaurantbackendapplication1.imagehosting.repository.RestaurantImageHostingConfigRepository;
import com.example.restaurantbackendapplication1.imagehosting.service.RestaurantImageHostingConfigService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Slf4j
public class RestaurantImageHostingConfigServiceImpl implements RestaurantImageHostingConfigService {

    private static final Set<String> ALLOWED_SORT_FIELDS = RestaurantImageHostingConfigSortField.allowedFields();

    private final RestaurantImageHostingConfigRepository repository;

    public RestaurantImageHostingConfigServiceImpl(RestaurantImageHostingConfigRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateRestaurantImageHostingConfigRequest request) {
        request.getProvider().validate(request.getConfig());
        RestaurantImageHostingConfigEntity entity = RestaurantImageHostingConfigMapper.create(request);
        repository.save(entity);
        log.info("RestaurantImageHostingConfig created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public RestaurantImageHostingConfigEntity getEntityById(Long id) {
        return repository.findByIdAndIsActiveAndIsDeleted(id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("RestaurantImageHostingConfig not found with id: " + id));
    }

    @Override
    public RestaurantImageHostingConfigResponse getById(Long id) {
        RestaurantImageHostingConfigEntity entity = getEntityById(id);
        RestaurantImageHostingConfigDto dto = RestaurantImageHostingConfigMapper.toDto(entity);
        return new RestaurantImageHostingConfigResponse(dto);
    }

    @Override
    public PaginatedResponse<RestaurantImageHostingConfigSummary> getAll(PaginatedRequest request) {
        Page<@NonNull RestaurantImageHostingConfigSummary> page = repository.findAllByIsActiveAndIsDeleted(
                true, false, request.toPageable(ALLOWED_SORT_FIELDS)
        );
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(RestaurantImageHostingConfigEntity entity,
                                  UpdateRestaurantImageHostingConfigRequest request) {
        RestaurantImageHostingConfigMapper.update(entity, request);
        repository.save(entity);
        log.info("RestaurantImageHostingConfig updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(Long id) {
        RestaurantImageHostingConfigEntity entity = getEntityById(id);
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        repository.save(entity);
        log.info("RestaurantImageHostingConfig soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
