package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menusection.CreateMenuSectionRequest;
import com.example.restaurantbackendapplication1.dto.request.menusection.UpdateMenuSectionRequest;
import com.example.restaurantbackendapplication1.dto.response.MenuSectionResponse;
import com.example.restaurantbackendapplication1.model.dto.MenuSectionDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionEntity;
import com.example.restaurantbackendapplication1.model.enums.MenuSectionSortField;
import com.example.restaurantbackendapplication1.model.mapper.MenuSectionMapper;
import com.example.restaurantbackendapplication1.model.projection.MenuSectionSummary;
import com.example.restaurantbackendapplication1.repository.MenuSectionRepository;
import com.example.restaurantbackendapplication1.service.MenuSectionService;
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
public class MenuSectionServiceImpl implements MenuSectionService {

    private static final Set<String> ALLOWED_SORT_FIELDS = MenuSectionSortField.allowedFields();

    private final MenuSectionRepository menuSectionRepository;

    public MenuSectionServiceImpl(MenuSectionRepository menuSectionRepository) {
        this.menuSectionRepository = menuSectionRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(MenuEntity menuEntity, CreateMenuSectionRequest request,
                                  Map<Long, LocaleEntity> localeEntityMap) {
        MenuSectionEntity entity = MenuSectionMapper.fromRequest(request, menuEntity, localeEntityMap);
        menuSectionRepository.save(entity);
        log.info("MenuSection created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public MenuSectionEntity getEntityById(Long id, MenuEntity menuEntity) {
        return menuSectionRepository.findByIdAndMenuEntityAndIsActiveAndIsDeleted(id, menuEntity, true, false)
                .orElseThrow(() -> new EntityNotFoundException("MenuSection not found with id: " + id));
    }

    @Override
    public MenuSectionResponse getById(Long id, MenuEntity menuEntity) {
        MenuSectionEntity entity = getEntityById(id, menuEntity);
        MenuSectionDto dto = MenuSectionMapper.toDto(entity);
        return new MenuSectionResponse(dto);
    }

    @Override
    public PaginatedResponse<MenuSectionSummary> getAll(MenuEntity menuEntity, PaginatedRequest request) {
        Page<@NonNull MenuSectionSummary> page = menuSectionRepository.findAllByMenuEntityAndIsActiveAndIsDeleted(
                menuEntity, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(MenuSectionEntity entity, UpdateMenuSectionRequest request) {
        MenuSectionMapper.update(entity, request);
        menuSectionRepository.save(entity);
        log.info("MenuSection updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(MenuSectionEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        menuSectionRepository.save(entity);
        log.info("MenuSection soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
