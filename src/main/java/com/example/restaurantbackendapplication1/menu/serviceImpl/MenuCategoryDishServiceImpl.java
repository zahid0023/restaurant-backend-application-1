package com.example.restaurantbackendapplication1.menu.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dish.model.dto.DishDto;
import com.example.restaurantbackendapplication1.dish.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.dish.model.enums.DishSortField;
import com.example.restaurantbackendapplication1.dish.model.mapper.DishMapper;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuCategoryDishEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.menu.model.mapper.MenuCategoryDishMapper;
import com.example.restaurantbackendapplication1.menu.repository.MenuCategoryDishRepository;
import com.example.restaurantbackendapplication1.menu.service.MenuCategoryDishService;
import com.example.restaurantbackendapplication1.commons.utils.Pagination;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Slf4j
public class MenuCategoryDishServiceImpl implements MenuCategoryDishService {

    private static final Set<String> ALLOWED_SORT_FIELDS = DishSortField.allowedFields();

    private final MenuCategoryDishRepository menuCategoryDishRepository;

    public MenuCategoryDishServiceImpl(MenuCategoryDishRepository menuCategoryDishRepository) {
        this.menuCategoryDishRepository = menuCategoryDishRepository;
    }

    @Transactional
    @Override
    public SuccessResponse assign(MenuCategoryEntity menuCategory, DishEntity dish) {
        if (menuCategoryDishRepository.existsByMenuCategoryEntity_IdAndDishEntity_IdAndIsActiveAndIsDeleted(
                menuCategory.getId(), dish.getId(), true, false)) {
            throw new IllegalStateException(
                    "Dish " + dish.getId() + " is already assigned to MenuCategory " + menuCategory.getId());
        }
        MenuCategoryDishEntity entity = MenuCategoryDishMapper.create(menuCategory, dish);
        menuCategoryDishRepository.save(entity);
        log.info("Dish {} assigned to MenuCategory {} with assignment id: {}",
                dish.getId(), menuCategory.getId(), entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public MenuCategoryDishEntity getEntityById(Long menuCategoryId, Long dishId) {
        return menuCategoryDishRepository
                .findByMenuCategoryEntity_IdAndDishEntity_IdAndIsActiveAndIsDeleted(menuCategoryId, dishId, true, false)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Dish " + dishId + " is not assigned to MenuCategory " + menuCategoryId));
    }

    @Transactional
    @Override
    public SuccessResponse unassign(MenuCategoryDishEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        menuCategoryDishRepository.save(entity);
        log.info("Dish unassigned from MenuCategory, assignment id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public PaginatedResponse<DishDto> getAll(Long menuCategoryId, PaginatedRequest request) {
        Page<@NonNull DishEntity> page = menuCategoryDishRepository
                .findDishesByMenuCategoryId(menuCategoryId, request.toPageable(ALLOWED_SORT_FIELDS));
        Page<@NonNull DishDto> dishDtoPage = page.map(dish -> DishMapper.toDto(dish, true, false, true));
        return Pagination.buildPaginatedResponse(dishDtoPage);
    }
}
