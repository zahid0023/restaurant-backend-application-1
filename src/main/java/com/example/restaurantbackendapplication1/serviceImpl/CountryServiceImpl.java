package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.country.CreateCountryRequest;
import com.example.restaurantbackendapplication1.dto.request.country.UpdateCountryRequest;
import com.example.restaurantbackendapplication1.dto.request.countrylocale.CountryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.CountryResponse;
import com.example.restaurantbackendapplication1.model.dto.CountryDto;
import com.example.restaurantbackendapplication1.model.projection.CountrySummary;
import com.example.restaurantbackendapplication1.model.entity.CountryEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.enums.CountrySortField;
import com.example.restaurantbackendapplication1.model.mapper.CountryMapper;
import com.example.restaurantbackendapplication1.repository.CountryRepository;
import com.example.restaurantbackendapplication1.service.CountryService;
import com.example.restaurantbackendapplication1.service.LocaleService;
import com.example.restaurantbackendapplication1.utils.EntityValidator;
import com.example.restaurantbackendapplication1.utils.Pagination;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {

    private static final Set<String> ALLOWED_SORT_FIELDS = CountrySortField.allowedFields();

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateCountryRequest request, Map<Long, LocaleEntity> localeEntityMap) {
        CountryEntity entity = CountryMapper.fromRequest(request, localeEntityMap);
        countryRepository.save(entity);
        log.info("Country created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public CountryResponse getById(Long id) {
        CountryEntity entity = getEntityById(id);
        CountryDto dto = CountryMapper.toDto(entity);
        return new CountryResponse(dto);
    }

    @Override
    public PaginatedResponse<CountrySummary> getAll(PaginatedRequest request) {
        Page<@NonNull CountrySummary> page = countryRepository.findAllByIsActiveAndIsDeleted(
                true, false, request.toPageable(ALLOWED_SORT_FIELDS)
        );
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(CountryEntity entity, UpdateCountryRequest request) {
        CountryMapper.update(entity, request);
        countryRepository.save(entity);
        log.info("Country updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(Long id) {
        CountryEntity entity = getEntityById(id);
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        countryRepository.save(entity);
        log.info("Country soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public CountryEntity getEntityById(Long id) {
        return countryRepository.findByIdAndIsActiveAndIsDeleted(id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + id));
    }

    @Override
    public List<CountryEntity> getAll(Set<Long> ids) {
        List<CountryEntity> entities = countryRepository.findAllByIdInAndIsActiveAndIsDeleted(ids, true, false);
        EntityValidator.validateAllFound(ids, entities, CountryEntity::getId, "Country");
        return entities;
    }
}
