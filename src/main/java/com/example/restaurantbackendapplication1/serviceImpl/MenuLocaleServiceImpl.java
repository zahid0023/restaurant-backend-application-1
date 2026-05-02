package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menulocale.CreateMenuLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menulocale.UpdateMenuLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.MenuLocaleResponse;
import com.example.restaurantbackendapplication1.model.dto.MenuLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuLocaleEntity;
import com.example.restaurantbackendapplication1.model.enums.MenuLocaleSortField;
import com.example.restaurantbackendapplication1.model.mapper.MenuLocaleMapper;
import com.example.restaurantbackendapplication1.model.projection.MenuLocaleSummary;
import com.example.restaurantbackendapplication1.repository.MenuLocaleRepository;
import com.example.restaurantbackendapplication1.service.MenuLocaleService;
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
public class MenuLocaleServiceImpl implements MenuLocaleService {

    private static final Set<String> ALLOWED_SORT_FIELDS = MenuLocaleSortField.allowedFields();

    private final MenuLocaleRepository menuLocaleRepository;

    public MenuLocaleServiceImpl(MenuLocaleRepository menuLocaleRepository) {
        this.menuLocaleRepository = menuLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(MenuEntity menuEntity, LocaleEntity localeEntity, CreateMenuLocaleRequest request) {
        MenuLocaleEntity entity = MenuLocaleMapper.fromRequest(request, menuEntity, localeEntity);
        menuLocaleRepository.save(entity);
        log.info("MenuLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public MenuLocaleEntity getEntityById(Long id, MenuEntity menuEntity) {
        return menuLocaleRepository.findByIdAndMenuEntityAndIsActiveAndIsDeleted(id, menuEntity, true, false)
                .orElseThrow(() -> new EntityNotFoundException("MenuLocale not found with id: " + id));
    }

    @Override
    public MenuLocaleResponse getById(Long id, MenuEntity menuEntity) {
        MenuLocaleEntity entity = getEntityById(id, menuEntity);
        MenuLocaleDto dto = MenuLocaleMapper.toDto(entity);
        return new MenuLocaleResponse(dto);
    }

    @Override
    public PaginatedResponse<MenuLocaleSummary> getAll(MenuEntity menuEntity, PaginatedRequest request) {
        Page<@NonNull MenuLocaleSummary> page = menuLocaleRepository.findAllByMenuEntityAndIsActiveAndIsDeleted(
                menuEntity, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(MenuLocaleEntity entity, LocaleEntity localeEntity, UpdateMenuLocaleRequest request) {
        MenuLocaleMapper.update(entity, request, localeEntity);
        menuLocaleRepository.save(entity);
        log.info("MenuLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(MenuLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        menuLocaleRepository.save(entity);
        log.info("MenuLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
