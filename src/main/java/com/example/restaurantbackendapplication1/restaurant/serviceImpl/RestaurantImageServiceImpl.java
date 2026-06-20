package com.example.restaurantbackendapplication1.restaurant.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.commons.utils.Pagination;
import com.example.restaurantbackendapplication1.imagehosting.dto.request.ImageRequest;
import com.example.restaurantbackendapplication1.imagehosting.model.entity.RestaurantImageHostingConfigEntity;
import com.example.restaurantbackendapplication1.imagehosting.service.ImageUploadService;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantimage.UpdateRestaurantImageRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.response.RestaurantImageResponse;
import com.example.restaurantbackendapplication1.restaurant.model.dto.RestaurantImageDto;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantImageEntity;
import com.example.restaurantbackendapplication1.restaurant.model.enums.RestaurantImageSortField;
import com.example.restaurantbackendapplication1.restaurant.model.mapper.RestaurantImageMapper;
import com.example.restaurantbackendapplication1.restaurant.model.projection.RestaurantImageSummary;
import com.example.restaurantbackendapplication1.restaurant.repository.RestaurantImageRepository;
import com.example.restaurantbackendapplication1.restaurant.service.RestaurantImageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class RestaurantImageServiceImpl implements RestaurantImageService {

    private static final Set<String> ALLOWED_SORT_FIELDS = RestaurantImageSortField.allowedFields();

    private final RestaurantImageRepository restaurantImageRepository;
    private final ImageUploadService imageUploadService;

    public RestaurantImageServiceImpl(RestaurantImageRepository restaurantImageRepository,
                                      ImageUploadService imageUploadService) {
        this.restaurantImageRepository = restaurantImageRepository;
        this.imageUploadService = imageUploadService;
    }

    @Transactional
    @Override
    public List<RestaurantImageDto> createAll(List<ImageRequest> imageRequests,
                                              RestaurantImageHostingConfigEntity config) {
        List<RestaurantImageEntity> entities = imageRequests.stream()
                .map(req -> RestaurantImageMapper.create(req, config))
                .toList();
        restaurantImageRepository.saveAll(entities);
        log.info("RestaurantImages created: {} images", entities.size());
        return entities.stream().map(RestaurantImageMapper::toDto).toList();
    }

    @Override
    public RestaurantImageEntity getEntityById(Long id) {
        return restaurantImageRepository.findByIdAndIsActiveAndIsDeleted(id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant image not found with id: " + id));
    }

    @Override
    public RestaurantImageResponse getById(Long id) {
        RestaurantImageDto dto = RestaurantImageMapper.toDto(getEntityById(id));
        return new RestaurantImageResponse(dto);
    }

    @Override
    public PaginatedResponse<RestaurantImageSummary> getAll(PaginatedRequest request) {
        Page<RestaurantImageSummary> page = restaurantImageRepository.findAllByIsActiveAndIsDeleted(
                true, false, request.toPageable(ALLOWED_SORT_FIELDS)
        );
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(RestaurantImageEntity entity, UpdateRestaurantImageRequest request) {
        RestaurantImageMapper.update(entity, request);
        restaurantImageRepository.save(entity);
        log.info("RestaurantImage updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(RestaurantImageEntity entity) {
        if (entity.getExternalId() != null) {
            imageUploadService.delete(
                    entity.getExternalId(),
                    entity.getConfigEntity().getProvider(),
                    entity.getConfigEntity().getConfig()
            );
        }
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        restaurantImageRepository.save(entity);
        log.info("RestaurantImage soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
