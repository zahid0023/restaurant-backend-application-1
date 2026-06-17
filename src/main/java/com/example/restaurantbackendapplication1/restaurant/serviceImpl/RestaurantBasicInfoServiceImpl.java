package com.example.restaurantbackendapplication1.restaurant.serviceImpl;

import com.example.restaurantbackendapplication1.address.model.entity.CityEntity;
import com.example.restaurantbackendapplication1.address.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.commons.utils.Pagination;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfo.CreateRestaurantBasicInfoRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfo.UpdateRestaurantBasicInfoRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.response.RestaurantBasicInfoResponse;
import com.example.restaurantbackendapplication1.restaurant.model.dto.RestaurantBasicInfoDto;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantBasicInfoEntity;
import com.example.restaurantbackendapplication1.restaurant.model.enums.RestaurantBasicInfoSortField;
import com.example.restaurantbackendapplication1.restaurant.model.mapper.RestaurantBasicInfoMapper;
import com.example.restaurantbackendapplication1.restaurant.model.projection.RestaurantBasicInfoSummary;
import com.example.restaurantbackendapplication1.restaurant.repository.RestaurantBasicInfoRepository;
import com.example.restaurantbackendapplication1.restaurant.service.RestaurantBasicInfoService;
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
public class RestaurantBasicInfoServiceImpl implements RestaurantBasicInfoService {

    private static final Set<String> ALLOWED_SORT_FIELDS = RestaurantBasicInfoSortField.allowedFields();

    private final RestaurantBasicInfoRepository restaurantBasicInfoRepository;

    public RestaurantBasicInfoServiceImpl(RestaurantBasicInfoRepository restaurantBasicInfoRepository) {
        this.restaurantBasicInfoRepository = restaurantBasicInfoRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateRestaurantBasicInfoRequest request,
                                  CountryEntity countryEntity,
                                  CityEntity cityEntity,
                                  Map<Long, LocaleEntity> localeEntityMap) {
        RestaurantBasicInfoEntity entity = RestaurantBasicInfoMapper.create(request, countryEntity, cityEntity, localeEntityMap);
        restaurantBasicInfoRepository.save(entity);
        log.info("RestaurantBasicInfo created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public RestaurantBasicInfoEntity getEntityById(Long id) {
        return restaurantBasicInfoRepository.findByIdAndIsActiveAndIsDeleted(id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("RestaurantBasicInfo not found with id: " + id));
    }

    @Override
    public RestaurantBasicInfoResponse getById(Long id) {
        RestaurantBasicInfoEntity entity = getEntityById(id);
        RestaurantBasicInfoDto dto = RestaurantBasicInfoMapper.toDto(entity);
        return new RestaurantBasicInfoResponse(dto);
    }

    @Override
    public PaginatedResponse<RestaurantBasicInfoSummary> getAll(PaginatedRequest request) {
        Page<@NonNull RestaurantBasicInfoSummary> page = restaurantBasicInfoRepository.findAllByIsActiveAndIsDeleted(
                true, false, request.toPageable(ALLOWED_SORT_FIELDS)
        );
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(RestaurantBasicInfoEntity entity, UpdateRestaurantBasicInfoRequest request) {
        RestaurantBasicInfoMapper.update(entity, request);
        restaurantBasicInfoRepository.save(entity);
        log.info("RestaurantBasicInfo updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(Long id) {
        RestaurantBasicInfoEntity entity = getEntityById(id);
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        restaurantBasicInfoRepository.save(entity);
        log.info("RestaurantBasicInfo soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
