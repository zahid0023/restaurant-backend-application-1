package com.example.restaurantbackendapplication1.menu.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.menu.dto.request.menucategory.CreateMenuCategoryRequest;
import com.example.restaurantbackendapplication1.menu.dto.request.menucategory.UpdateMenuCategoryRequest;
import com.example.restaurantbackendapplication1.menu.dto.response.MenuCategoryResponse;
import com.example.restaurantbackendapplication1.menu.model.dto.MenuCategoryDto;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuTypeEntity;
import com.example.restaurantbackendapplication1.menu.model.enums.MenuCategorySortField;
import com.example.restaurantbackendapplication1.menu.model.mapper.MenuCategoryMapper;
import com.example.restaurantbackendapplication1.menu.model.projection.MenuCategorySummary;
import com.example.restaurantbackendapplication1.menu.repository.MenuCategoryRepository;
import com.example.restaurantbackendapplication1.menu.service.MenuCategoryService;
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
public class MenuCategoryServiceImpl implements MenuCategoryService {

    private static final Set<String> ALLOWED_SORT_FIELDS = MenuCategorySortField.allowedFields();

    private final MenuCategoryRepository menuCategoryRepository;

    public MenuCategoryServiceImpl(MenuCategoryRepository menuCategoryRepository) {
        this.menuCategoryRepository = menuCategoryRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateMenuCategoryRequest request,
                                  MenuTypeEntity menuTypeEntity,
                                  Map<Long, LocaleEntity> localeEntityMap) {
        MenuCategoryEntity entity = MenuCategoryMapper.create(request, menuTypeEntity, localeEntityMap);
        menuCategoryRepository.save(entity);
        log.info("MenuCategory created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public MenuCategoryEntity getEntityById(Long id) {
        return menuCategoryRepository.findByIdAndIsActiveAndIsDeleted(id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("MenuCategory not found with id: " + id));
    }

    @Override
    public MenuCategoryResponse getById(Long id) {
        MenuCategoryEntity entity = getEntityById(id);
        MenuCategoryDto dto = MenuCategoryMapper.toDto(entity);
        return new MenuCategoryResponse(dto);
    }

    @Override
    public PaginatedResponse<MenuCategorySummary> getAll(PaginatedRequest request) {
        Page<@NonNull MenuCategorySummary> page = menuCategoryRepository
                .findAllByIsActiveAndIsDeleted(true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Override
    public PaginatedResponse<MenuCategorySummary> getAll(Long menuTypeId, PaginatedRequest request) {
        Page<@NonNull MenuCategorySummary> page = menuCategoryRepository.findAllByMenuTypeEntity_IdAndIsActiveAndIsDeleted(menuTypeId, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(MenuCategoryEntity entity, UpdateMenuCategoryRequest request) {
        MenuCategoryMapper.update(entity, request);
        menuCategoryRepository.save(entity);
        log.info("MenuCategory updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(Long id) {
        MenuCategoryEntity entity = getEntityById(id);
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        menuCategoryRepository.save(entity);
        log.info("MenuCategory soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
