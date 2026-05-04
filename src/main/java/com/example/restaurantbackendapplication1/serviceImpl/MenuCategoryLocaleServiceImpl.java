package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menucategorylocale.CreateMenuCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menucategorylocale.UpdateMenuCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.MenuCategoryLocaleResponse;
import com.example.restaurantbackendapplication1.model.dto.MenuCategoryLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryLocaleEntity;
import com.example.restaurantbackendapplication1.model.enums.MenuCategoryLocaleSortField;
import com.example.restaurantbackendapplication1.model.mapper.MenuCategoryLocaleMapper;
import com.example.restaurantbackendapplication1.model.projection.MenuCategoryLocaleSummary;
import com.example.restaurantbackendapplication1.repository.MenuCategoryLocaleRepository;
import com.example.restaurantbackendapplication1.service.MenuCategoryLocaleService;
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
public class MenuCategoryLocaleServiceImpl implements MenuCategoryLocaleService {

    private static final Set<String> ALLOWED_SORT_FIELDS = MenuCategoryLocaleSortField.allowedFields();

    private final MenuCategoryLocaleRepository menuCategoryLocaleRepository;

    public MenuCategoryLocaleServiceImpl(MenuCategoryLocaleRepository menuCategoryLocaleRepository) {
        this.menuCategoryLocaleRepository = menuCategoryLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(MenuCategoryEntity menuCategoryEntity,
                                  LocaleEntity localeEntity,
                                  CreateMenuCategoryLocaleRequest request) {
        MenuCategoryLocaleEntity entity = MenuCategoryLocaleMapper.fromRequest(request, menuCategoryEntity, localeEntity);
        menuCategoryLocaleRepository.save(entity);
        log.info("MenuCategoryLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public MenuCategoryLocaleEntity getEntityById(Long menuCategoryId, Long id) {
        return menuCategoryLocaleRepository
                .findByMenuCategoryEntity_IdAndIdAndIsActiveAndIsDeleted(menuCategoryId, id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("MenuCategory locale not found with id: " + id));
    }

    @Override
    public MenuCategoryLocaleResponse getById(Long menuCategoryId, Long id) {
        MenuCategoryLocaleEntity entity = getEntityById(menuCategoryId, id);
        MenuCategoryLocaleDto dto = MenuCategoryLocaleMapper.toDto(entity);
        return new MenuCategoryLocaleResponse(dto);
    }

    @Override
    public PaginatedResponse<MenuCategoryLocaleSummary> getAll(Long menuCategoryId,
                                                               PaginatedRequest request) {
        Page<@NonNull MenuCategoryLocaleSummary> page = menuCategoryLocaleRepository.findAllByMenuCategoryEntity_IdAndIsActiveAndIsDeleted(menuCategoryId, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(MenuCategoryLocaleEntity entity,
                                  LocaleEntity localeEntity,
                                  UpdateMenuCategoryLocaleRequest request) {
        MenuCategoryLocaleMapper.update(entity, request, localeEntity);
        menuCategoryLocaleRepository.save(entity);
        log.info("MenuCategoryLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(MenuCategoryLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        menuCategoryLocaleRepository.save(entity);
        log.info("MenuCategoryLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
