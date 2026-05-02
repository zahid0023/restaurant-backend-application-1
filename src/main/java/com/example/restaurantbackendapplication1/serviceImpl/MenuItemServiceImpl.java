package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menuitem.CreateMenuItemRequest;
import com.example.restaurantbackendapplication1.dto.request.menuitem.UpdateMenuItemRequest;
import com.example.restaurantbackendapplication1.dto.response.MenuItemResponse;
import com.example.restaurantbackendapplication1.model.dto.MenuItemDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuItemEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionEntity;
import com.example.restaurantbackendapplication1.model.enums.MenuItemSortField;
import com.example.restaurantbackendapplication1.model.mapper.MenuItemMapper;
import com.example.restaurantbackendapplication1.model.projection.MenuItemSummary;
import com.example.restaurantbackendapplication1.repository.MenuItemRepository;
import com.example.restaurantbackendapplication1.service.MenuItemService;
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
public class MenuItemServiceImpl implements MenuItemService {

    private static final Set<String> ALLOWED_SORT_FIELDS = MenuItemSortField.allowedFields();

    private final MenuItemRepository menuItemRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(MenuSectionEntity menuSectionEntity, CreateMenuItemRequest request,
                                  Map<Long, LocaleEntity> localeEntityMap) {
        MenuItemEntity entity = MenuItemMapper.fromRequest(request, menuSectionEntity, localeEntityMap);
        menuItemRepository.save(entity);
        log.info("MenuItem created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public MenuItemEntity getEntityById(Long id, MenuSectionEntity menuSectionEntity) {
        return menuItemRepository.findByIdAndMenuSectionEntityAndIsActiveAndIsDeleted(id, menuSectionEntity, true, false)
                .orElseThrow(() -> new EntityNotFoundException("MenuItem not found with id: " + id));
    }

    @Override
    public MenuItemResponse getById(Long id, MenuSectionEntity menuSectionEntity) {
        MenuItemEntity entity = getEntityById(id, menuSectionEntity);
        MenuItemDto dto = MenuItemMapper.toDto(entity);
        return new MenuItemResponse(dto);
    }

    @Override
    public PaginatedResponse<MenuItemSummary> getAll(MenuSectionEntity menuSectionEntity, PaginatedRequest request) {
        Page<@NonNull MenuItemSummary> page = menuItemRepository.findAllByMenuSectionEntityAndIsActiveAndIsDeleted(
                menuSectionEntity, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(MenuItemEntity entity, UpdateMenuItemRequest request) {
        MenuItemMapper.update(entity, request);
        menuItemRepository.save(entity);
        log.info("MenuItem updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(MenuItemEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        menuItemRepository.save(entity);
        log.info("MenuItem soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
