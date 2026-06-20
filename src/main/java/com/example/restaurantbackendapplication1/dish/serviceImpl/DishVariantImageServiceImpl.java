package com.example.restaurantbackendapplication1.dish.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.commons.utils.Pagination;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantimage.UpdateDishVariantImageRequest;
import com.example.restaurantbackendapplication1.dish.dto.response.DishVariantImageResponse;
import com.example.restaurantbackendapplication1.dish.model.dto.DishVariantImageDto;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantImageEntity;
import com.example.restaurantbackendapplication1.dish.model.enums.DishVariantImageSortField;
import com.example.restaurantbackendapplication1.dish.model.mapper.DishVariantImageMapper;
import com.example.restaurantbackendapplication1.dish.model.projection.DishVariantImageSummary;
import com.example.restaurantbackendapplication1.dish.repository.DishVariantImageRepository;
import com.example.restaurantbackendapplication1.dish.service.DishVariantImageService;
import com.example.restaurantbackendapplication1.imagehosting.dto.request.ImageRequest;
import com.example.restaurantbackendapplication1.imagehosting.model.entity.RestaurantImageHostingConfigEntity;
import com.example.restaurantbackendapplication1.imagehosting.service.ImageUploadService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class DishVariantImageServiceImpl implements DishVariantImageService {

    private static final Set<String> ALLOWED_SORT_FIELDS = DishVariantImageSortField.allowedFields();

    private final DishVariantImageRepository dishVariantImageRepository;
    private final ImageUploadService imageUploadService;

    public DishVariantImageServiceImpl(DishVariantImageRepository dishVariantImageRepository,
                                       ImageUploadService imageUploadService) {
        this.dishVariantImageRepository = dishVariantImageRepository;
        this.imageUploadService = imageUploadService;
    }

    @Transactional
    @Override
    public List<DishVariantImageDto> createAll(List<ImageRequest> imageRequests,
                                               RestaurantImageHostingConfigEntity config,
                                               DishVariantEntity dishVariantEntity) {
        List<DishVariantImageEntity> entities = imageRequests.stream()
                .map(req -> DishVariantImageMapper.create(req, config, dishVariantEntity))
                .toList();
        dishVariantImageRepository.saveAll(entities);
        log.info("DishVariantImages created: {} images for variant id: {}", entities.size(), dishVariantEntity.getId());
        return entities.stream().map(DishVariantImageMapper::toDto).toList();
    }

    @Override
    public DishVariantImageEntity getEntityById(Long dishVariantId, Long id) {
        return dishVariantImageRepository.findByIdAndDishVariantEntity_IdAndIsActiveAndIsDeleted(id, dishVariantId, true, false)
                .orElseThrow(() -> new EntityNotFoundException("Dish variant image not found with id: " + id));
    }

    @Override
    public DishVariantImageResponse getById(Long dishVariantId, Long id) {
        DishVariantImageDto dto = DishVariantImageMapper.toDto(getEntityById(dishVariantId, id));
        return new DishVariantImageResponse(dto);
    }

    @Override
    public PaginatedResponse<DishVariantImageSummary> getAll(Long dishVariantId, PaginatedRequest request) {
        Page<DishVariantImageSummary> page = dishVariantImageRepository.findAllByDishVariantEntity_IdAndIsActiveAndIsDeleted(
                dishVariantId, true, false, request.toPageable(ALLOWED_SORT_FIELDS)
        );
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(DishVariantImageEntity entity, UpdateDishVariantImageRequest request) {
        DishVariantImageMapper.update(entity, request);
        dishVariantImageRepository.save(entity);
        log.info("DishVariantImage updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(DishVariantImageEntity entity) {
        if (entity.getExternalId() != null) {
            imageUploadService.delete(
                    entity.getExternalId(),
                    entity.getConfigEntity().getProvider(),
                    entity.getConfigEntity().getConfig()
            );
        }
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        dishVariantImageRepository.save(entity);
        log.info("DishVariantImage soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
