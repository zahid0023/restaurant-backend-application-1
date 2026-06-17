package com.example.restaurantbackendapplication1.restaurant.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfolocale.CreateRestaurantBasicInfoLocaleRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfolocale.UpdateRestaurantBasicInfoLocaleRequest;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantBasicInfoEntity;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantBasicInfoLocaleEntity;
import com.example.restaurantbackendapplication1.restaurant.model.mapper.RestaurantBasicInfoLocaleMapper;
import com.example.restaurantbackendapplication1.restaurant.repository.RestaurantBasicInfoLocaleRepository;
import com.example.restaurantbackendapplication1.restaurant.service.RestaurantBasicInfoLocaleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class RestaurantBasicInfoLocaleServiceImpl implements RestaurantBasicInfoLocaleService {

    private final RestaurantBasicInfoLocaleRepository restaurantBasicInfoLocaleRepository;

    public RestaurantBasicInfoLocaleServiceImpl(RestaurantBasicInfoLocaleRepository restaurantBasicInfoLocaleRepository) {
        this.restaurantBasicInfoLocaleRepository = restaurantBasicInfoLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(RestaurantBasicInfoEntity restaurantBasicInfoEntity,
                                  LocaleEntity localeEntity,
                                  CreateRestaurantBasicInfoLocaleRequest request) {
        RestaurantBasicInfoLocaleEntity entity = RestaurantBasicInfoLocaleMapper.create(request, restaurantBasicInfoEntity, localeEntity);
        restaurantBasicInfoLocaleRepository.save(entity);
        log.info("RestaurantBasicInfoLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public RestaurantBasicInfoLocaleEntity getEntityById(Long restaurantBasicInfoId, Long id) {
        return restaurantBasicInfoLocaleRepository
                .findByRestaurantBasicInfoEntity_IdAndIdAndIsActiveAndIsDeleted(restaurantBasicInfoId, id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("RestaurantBasicInfoLocale not found with id: " + id));
    }

    @Transactional
    @Override
    public SuccessResponse update(RestaurantBasicInfoLocaleEntity entity, UpdateRestaurantBasicInfoLocaleRequest request) {
        RestaurantBasicInfoLocaleMapper.update(entity, request);
        restaurantBasicInfoLocaleRepository.save(entity);
        log.info("RestaurantBasicInfoLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(RestaurantBasicInfoLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        restaurantBasicInfoLocaleRepository.save(entity);
        log.info("RestaurantBasicInfoLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
