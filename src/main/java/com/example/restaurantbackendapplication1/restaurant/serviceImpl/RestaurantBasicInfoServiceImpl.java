package com.example.restaurantbackendapplication1.restaurant.serviceImpl;

import com.example.restaurantbackendapplication1.address.model.entity.CityEntity;
import com.example.restaurantbackendapplication1.address.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.imagehosting.dto.response.ImageUploadResponse;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfo.UpdateRestaurantBasicInfoRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.response.RestaurantBasicInfoResponse;
import com.example.restaurantbackendapplication1.restaurant.model.dto.RestaurantBasicInfoDto;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantBasicInfoEntity;
import com.example.restaurantbackendapplication1.restaurant.model.mapper.RestaurantBasicInfoMapper;
import com.example.restaurantbackendapplication1.restaurant.repository.RestaurantBasicInfoRepository;
import com.example.restaurantbackendapplication1.restaurant.service.RestaurantBasicInfoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class RestaurantBasicInfoServiceImpl implements RestaurantBasicInfoService {

    private static final Long RESTAURANT_ID = 1L;

    private final RestaurantBasicInfoRepository restaurantBasicInfoRepository;

    public RestaurantBasicInfoServiceImpl(RestaurantBasicInfoRepository restaurantBasicInfoRepository) {
        this.restaurantBasicInfoRepository = restaurantBasicInfoRepository;
    }

    @Override
    public RestaurantBasicInfoEntity getEntity() {
        return restaurantBasicInfoRepository.findByIdAndIsActiveAndIsDeleted(RESTAURANT_ID, true, false)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant basic info not found"));
    }

    @Override
    public RestaurantBasicInfoResponse get() {
        RestaurantBasicInfoDto dto = RestaurantBasicInfoMapper.toDto(getEntity());
        return new RestaurantBasicInfoResponse(dto);
    }

    @Transactional
    @Override
    public SuccessResponse update(RestaurantBasicInfoEntity entity,
                                  UpdateRestaurantBasicInfoRequest request,
                                  CountryEntity countryEntity,
                                  CityEntity cityEntity) {
        RestaurantBasicInfoMapper.update(entity, request, countryEntity, cityEntity);
        restaurantBasicInfoRepository.save(entity);
        log.info("RestaurantBasicInfo updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse uploadLogo(RestaurantBasicInfoEntity entity, ImageUploadResponse imageUploadResponse) {
        entity.setLogoUrl(imageUploadResponse.getImageUrl());
        restaurantBasicInfoRepository.save(entity);
        log.info("RestaurantBasicInfo logo uploaded, url: {}", imageUploadResponse.getImageUrl());
        return new SuccessResponse(true, entity.getId());
    }
}
