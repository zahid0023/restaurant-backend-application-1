package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuMenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.enums.MenuMenuCategorySortField;
import com.example.restaurantbackendapplication1.model.mapper.MenuMenuCategoryMapper;
import com.example.restaurantbackendapplication1.model.projection.MenuCategorySummary;
import com.example.restaurantbackendapplication1.repository.MenuMenuCategoryRepository;
import com.example.restaurantbackendapplication1.service.MenuMenuCategoryService;
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
public class MenuMenuCategoryServiceImpl implements MenuMenuCategoryService {

    private static final Set<String> ALLOWED_SORT_FIELDS = MenuMenuCategorySortField.allowedFields();

    private final MenuMenuCategoryRepository menuMenuCategoryRepository;

    public MenuMenuCategoryServiceImpl(MenuMenuCategoryRepository menuMenuCategoryRepository) {
        this.menuMenuCategoryRepository = menuMenuCategoryRepository;
    }

    @Transactional
    @Override
    public SuccessResponse assign(MenuEntity menuEntity, MenuCategoryEntity menuCategoryEntity) {
        boolean alreadyAssigned = menuMenuCategoryRepository
                .existsByMenuEntityAndMenuCategoryEntityAndIsActiveAndIsDeleted(menuEntity, menuCategoryEntity, true, false);
        if (alreadyAssigned) {
            throw new IllegalStateException(
                    "MenuCategory " + menuCategoryEntity.getId() + " is already assigned to Menu " + menuEntity.getId());
        }
        MenuMenuCategoryEntity entity = MenuMenuCategoryMapper.fromRequest(menuEntity, menuCategoryEntity);
        menuMenuCategoryRepository.save(entity);
        log.info("MenuCategory {} assigned to Menu {}", menuCategoryEntity.getId(), menuEntity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public PaginatedResponse<MenuCategorySummary> getAllMenuCategories(Long menuId, PaginatedRequest request) {
        Page<@NonNull MenuCategorySummary> page = menuMenuCategoryRepository
                .findAllMenuCategoriesByMenuId(menuId, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Override
    public MenuMenuCategoryEntity getAssignment(MenuEntity menuEntity, MenuCategoryEntity menuCategoryEntity) {
        return menuMenuCategoryRepository
                .findByMenuEntityAndMenuCategoryEntityAndIsActiveAndIsDeleted(menuEntity, menuCategoryEntity, true, false)
                .orElseThrow(() -> new EntityNotFoundException(
                        "MenuCategory " + menuCategoryEntity.getId() + " is not assigned to Menu " + menuEntity.getId()));
    }

    @Transactional
    @Override
    public SuccessResponse unassign(MenuMenuCategoryEntity entity) {
        entity.setIsActive(false);
        entity.setIsDeleted(true);
        menuMenuCategoryRepository.save(entity);
        log.info("MenuCategory {} unassigned from Menu {}",
                entity.getMenuCategoryEntity().getId(),
                entity.getMenuEntity().getId());
        return new SuccessResponse(true, entity.getId());
    }
}
