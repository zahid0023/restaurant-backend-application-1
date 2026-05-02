package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.citylocale.CreateCityLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.citylocale.UpdateCityLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.CityLocaleResponse;
import com.example.restaurantbackendapplication1.model.dto.CityLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.CityEntity;
import com.example.restaurantbackendapplication1.model.entity.CityLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.enums.CityLocaleSortField;
import com.example.restaurantbackendapplication1.model.mapper.CityLocaleMapper;
import com.example.restaurantbackendapplication1.model.projection.CityLocaleSummary;
import com.example.restaurantbackendapplication1.repository.CityLocaleRepository;
import com.example.restaurantbackendapplication1.service.CityLocaleService;
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
public class CityLocaleServiceImpl implements CityLocaleService {

    private static final Set<String> ALLOWED_SORT_FIELDS = CityLocaleSortField.allowedFields();

    private final CityLocaleRepository cityLocaleRepository;

    public CityLocaleServiceImpl(CityLocaleRepository cityLocaleRepository) {
        this.cityLocaleRepository = cityLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CityEntity city, LocaleEntity locale, CreateCityLocaleRequest request) {
        CityLocaleEntity entity = CityLocaleMapper.fromRequest(request, city, locale);
        cityLocaleRepository.save(entity);
        log.info("CityLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public CityLocaleResponse getById(Long id, CityEntity city) {
        CityLocaleEntity entity = getEntityById(id, city);
        CityLocaleDto dto = CityLocaleMapper.toDto(entity);
        return new CityLocaleResponse(dto);
    }

    @Override
    public PaginatedResponse<CityLocaleSummary> getAll(CityEntity city, PaginatedRequest request) {
        Page<@NonNull CityLocaleSummary> page = cityLocaleRepository
                .findAllByCityEntityAndIsActiveAndIsDeleted(
                        city, true, false, request.toPageable(ALLOWED_SORT_FIELDS)
                );
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(CityLocaleEntity entity, LocaleEntity locale, UpdateCityLocaleRequest request) {
        CityLocaleMapper.update(entity, request, locale);
        cityLocaleRepository.save(entity);
        log.info("CityLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(CityLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        cityLocaleRepository.save(entity);
        log.info("CityLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public CityLocaleEntity getEntityById(Long id, CityEntity city) {
        return cityLocaleRepository
                .findByIdAndCityEntityAndIsActiveAndIsDeleted(id, city, true, false)
                .orElseThrow(() -> new EntityNotFoundException("CityLocale not found with id: " + id));
    }
}
