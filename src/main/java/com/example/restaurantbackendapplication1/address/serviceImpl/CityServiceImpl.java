package com.example.restaurantbackendapplication1.address.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.address.dto.request.city.CreateCityRequest;
import com.example.restaurantbackendapplication1.address.dto.request.city.UpdateCityRequest;
import com.example.restaurantbackendapplication1.address.dto.response.CityResponse;
import com.example.restaurantbackendapplication1.address.model.dto.CityDto;
import com.example.restaurantbackendapplication1.address.model.entity.CityEntity;
import com.example.restaurantbackendapplication1.address.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.address.model.enums.CitySortField;
import com.example.restaurantbackendapplication1.address.model.mapper.CityMapper;
import com.example.restaurantbackendapplication1.address.model.projection.CitySummary;
import com.example.restaurantbackendapplication1.address.repository.CityRepository;
import com.example.restaurantbackendapplication1.address.service.CityService;
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
public class CityServiceImpl implements CityService {

    private static final Set<String> ALLOWED_SORT_FIELDS = CitySortField.allowedFields();

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateCityRequest request,
                                  CountryEntity countryEntity,
                                  Map<Long, LocaleEntity> localeEntityMap) {
        CityEntity entity = CityMapper.create(request, countryEntity, localeEntityMap);
        cityRepository.save(entity);
        log.info("City created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public CityResponse getById(Long countryId, Long id) {
        CityEntity entity = getEntityById(countryId, id);
        CityDto dto = CityMapper.toDto(entity);
        return new CityResponse(dto);
    }

    @Override
    public PaginatedResponse<CitySummary> getAll(Long countryId, PaginatedRequest request) {
        Page<@NonNull CitySummary> page = cityRepository.findAllByCountryEntity_IdAndIsActiveAndIsDeleted(
                countryId, true, false, request.toPageable(ALLOWED_SORT_FIELDS)
        );
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(CityEntity entity, UpdateCityRequest request) {
        CityMapper.update(entity, request);
        cityRepository.save(entity);
        log.info("City updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(CityEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        cityRepository.save(entity);
        log.info("City soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public CityEntity getEntityById(Long countryId, Long id) {
        return cityRepository.findByCountryEntity_IdAndIdAndIsActiveAndIsDeleted(countryId, id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("City not found with id: " + id));
    }
}
