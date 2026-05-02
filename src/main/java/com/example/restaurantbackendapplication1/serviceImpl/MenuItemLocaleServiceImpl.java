package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menuitemlocale.CreateMenuItemLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menuitemlocale.UpdateMenuItemLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.MenuItemLocaleResponse;
import com.example.restaurantbackendapplication1.model.dto.MenuItemLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuItemEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuItemLocaleEntity;
import com.example.restaurantbackendapplication1.model.enums.MenuItemLocaleSortField;
import com.example.restaurantbackendapplication1.model.mapper.MenuItemLocaleMapper;
import com.example.restaurantbackendapplication1.model.projection.MenuItemLocaleSummary;
import com.example.restaurantbackendapplication1.repository.MenuItemLocaleRepository;
import com.example.restaurantbackendapplication1.service.MenuItemLocaleService;
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
public class MenuItemLocaleServiceImpl implements MenuItemLocaleService {

    private static final Set<String> ALLOWED_SORT_FIELDS = MenuItemLocaleSortField.allowedFields();

    private final MenuItemLocaleRepository menuItemLocaleRepository;

    public MenuItemLocaleServiceImpl(MenuItemLocaleRepository menuItemLocaleRepository) {
        this.menuItemLocaleRepository = menuItemLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(MenuItemEntity menuItemEntity, LocaleEntity localeEntity,
                                  CreateMenuItemLocaleRequest request) {
        MenuItemLocaleEntity entity = MenuItemLocaleMapper.fromRequest(request, menuItemEntity, localeEntity);
        menuItemLocaleRepository.save(entity);
        log.info("MenuItemLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public MenuItemLocaleEntity getEntityById(Long id, MenuItemEntity menuItemEntity) {
        return menuItemLocaleRepository
                .findByIdAndMenuItemEntityAndIsActiveAndIsDeleted(id, menuItemEntity, true, false)
                .orElseThrow(() -> new EntityNotFoundException("MenuItemLocale not found with id: " + id));
    }

    @Override
    public MenuItemLocaleResponse getById(Long id, MenuItemEntity menuItemEntity) {
        MenuItemLocaleEntity entity = getEntityById(id, menuItemEntity);
        MenuItemLocaleDto dto = MenuItemLocaleMapper.toDto(entity);
        return new MenuItemLocaleResponse(dto);
    }

    @Override
    public PaginatedResponse<MenuItemLocaleSummary> getAll(MenuItemEntity menuItemEntity, PaginatedRequest request) {
        Page<@NonNull MenuItemLocaleSummary> page = menuItemLocaleRepository
                .findAllByMenuItemEntityAndIsActiveAndIsDeleted(
                        menuItemEntity, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(MenuItemLocaleEntity entity, LocaleEntity localeEntity,
                                  UpdateMenuItemLocaleRequest request) {
        MenuItemLocaleMapper.update(entity, request, localeEntity);
        menuItemLocaleRepository.save(entity);
        log.info("MenuItemLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(MenuItemLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        menuItemLocaleRepository.save(entity);
        log.info("MenuItemLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
