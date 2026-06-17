package com.example.restaurantbackendapplication1.restaurant.model.mapper;

import com.example.restaurantbackendapplication1.address.model.entity.CityEntity;
import com.example.restaurantbackendapplication1.address.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.address.model.mapper.CityMapper;
import com.example.restaurantbackendapplication1.address.model.mapper.CountryMapper;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfo.RestaurantBasicInfoRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfo.UpdateRestaurantBasicInfoRequest;
import com.example.restaurantbackendapplication1.restaurant.model.dto.RestaurantBasicInfoDto;
import com.example.restaurantbackendapplication1.restaurant.model.dto.RestaurantBasicInfoLocaleDto;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantBasicInfoEntity;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class RestaurantBasicInfoMapper {

    public void update(RestaurantBasicInfoEntity entity,
                       UpdateRestaurantBasicInfoRequest request,
                       CountryEntity countryEntity,
                       CityEntity cityEntity) {
        applyCommonFields(entity, request, countryEntity, cityEntity);

    }

    private void applyCommonFields(RestaurantBasicInfoEntity entity,
                                   RestaurantBasicInfoRequest request,
                                   CountryEntity countryEntity,
                                   CityEntity cityEntity) {
        entity.setEstd(request.getEstd());
        entity.setCountryEntity(countryEntity);
        entity.setCityEntity(cityEntity);
        entity.setLat(request.getLat());
        entity.setLon(request.getLon());
        entity.setPhone(request.getPhone());
        entity.setEmail(request.getEmail());
    }

    public RestaurantBasicInfoDto toDto(RestaurantBasicInfoEntity entity) {
        List<RestaurantBasicInfoLocaleDto> locales = entity.getRestaurantBasicInfoLocaleEntities().stream()
                .map(RestaurantBasicInfoLocaleMapper::toDto)
                .toList();

        return RestaurantBasicInfoDto.builder()
                .id(entity.getId())
                .estd(entity.getEstd())
                .lat(entity.getLat())
                .lon(entity.getLon())
                .country(CountryMapper.toDto(entity.getCountryEntity()))
                .city(CityMapper.toDto(entity.getCityEntity()))
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .logoUrl(entity.getLogoUrl())
                .locales(locales)
                .build();
    }
}
