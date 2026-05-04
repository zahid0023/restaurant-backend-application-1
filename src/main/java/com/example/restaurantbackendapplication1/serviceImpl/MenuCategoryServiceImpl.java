package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menucategory.CreateMenuCategoryRequest;
import com.example.restaurantbackendapplication1.dto.request.menucategory.UpdateMenuCategoryRequest;
import com.example.restaurantbackendapplication1.dto.response.MenuCategoryResponse;
import com.example.restaurantbackendapplication1.model.dto.MenuCategoryDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.enums.MenuCategorySortField;
import com.example.restaurantbackendapplication1.model.mapper.MenuCategoryMapper;
import com.example.restaurantbackendapplication1.model.projection.MenuCategorySummary;
import com.example.restaurantbackendapplication1.repository.MenuCategoryRepository;
import com.example.restaurantbackendapplication1.service.MenuCategoryService;
import com.example.restaurantbackendapplication1.utils.Pagination;
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
                                  MenuEntity menuEntity,
                                  Map<Long, LocaleEntity> localeEntityMap) {
        MenuCategoryEntity entity = MenuCategoryMapper.fromRequest(request, menuEntity, localeEntityMap);
        menuCategoryRepository.save(entity);
        log.info("MenuCategory created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public MenuCategoryEntity getEntityById(Long menuId, Long id) {
        return menuCategoryRepository.findByMenuEntity_IdAndIdAndIsActiveAndIsDeleted(menuId, id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("MenuCategory not found with id: " + id));
    }

    @Override
    public MenuCategoryResponse getById(Long menuId, Long id) {
        MenuCategoryEntity entity = getEntityById(menuId, id);
        MenuCategoryDto dto = MenuCategoryMapper.toDto(entity);
        return new MenuCategoryResponse(dto);
    }

    @Override
    public PaginatedResponse<MenuCategorySummary> getAll(Long menuId, PaginatedRequest request) {
        Page<@NonNull MenuCategorySummary> page = menuCategoryRepository
                .findAllByMenuEntityIdAndIsActiveAndIsDeleted(menuId, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
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
    public SuccessResponse delete(Long menuId, Long id) {
        MenuCategoryEntity entity = getEntityById(menuId, id);
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        menuCategoryRepository.save(entity);
        log.info("MenuCategory soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
