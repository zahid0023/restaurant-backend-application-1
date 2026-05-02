package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.countrylocale.CreateCountryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.countrylocale.UpdateCountryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.CountryLocaleResponse;
import com.example.restaurantbackendapplication1.model.dto.CountryLocaleDto;
import com.example.restaurantbackendapplication1.model.projection.CountryLocaleSummary;
import com.example.restaurantbackendapplication1.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.model.entity.CountryLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.enums.CountryLocaleSortField;
import com.example.restaurantbackendapplication1.model.mapper.CountryLocaleMapper;
import com.example.restaurantbackendapplication1.repository.CountryLocaleRepository;
import com.example.restaurantbackendapplication1.service.CountryLocaleService;
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
public class CountryLocaleServiceImpl implements CountryLocaleService {

    private static final Set<String> ALLOWED_SORT_FIELDS = CountryLocaleSortField.allowedFields();

    private final CountryLocaleRepository countryLocaleRepository;

    public CountryLocaleServiceImpl(CountryLocaleRepository countryLocaleRepository) {
        this.countryLocaleRepository = countryLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CountryEntity country, LocaleEntity locale, CreateCountryLocaleRequest request) {
        CountryLocaleEntity entity = CountryLocaleMapper.fromRequest(request, country, locale);
        countryLocaleRepository.save(entity);
        log.info("CountryLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public CountryLocaleResponse getById(Long id, CountryEntity country) {
        CountryLocaleEntity entity = getEntityById(id, country);
        CountryLocaleDto dto = CountryLocaleMapper.toDto(entity);
        return new CountryLocaleResponse(dto);
    }

    @Override
    public PaginatedResponse<CountryLocaleSummary> getAll(CountryEntity country, PaginatedRequest request) {
        Page<@NonNull CountryLocaleSummary> page = countryLocaleRepository
                .findAllByCountryEntityAndIsActiveAndIsDeleted(
                        country, true, false, request.toPageable(ALLOWED_SORT_FIELDS)
                );
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(CountryLocaleEntity entity, LocaleEntity locale, UpdateCountryLocaleRequest request) {
        CountryLocaleMapper.update(entity, request, locale);
        countryLocaleRepository.save(entity);
        log.info("CountryLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(CountryLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        countryLocaleRepository.save(entity);
        log.info("CountryLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public CountryLocaleEntity getEntityById(Long id, CountryEntity country) {
        return countryLocaleRepository
                .findByIdAndCountryEntityAndIsActiveAndIsDeleted(id, country, true, false)
                .orElseThrow(() -> new EntityNotFoundException("CountryLocale not found with id: " + id));
    }
}
