package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menutype.CreateMenuTypeRequest;
import com.example.restaurantbackendapplication1.dto.request.menutype.UpdateMenuTypeRequest;
import com.example.restaurantbackendapplication1.dto.response.MenuTypeResponse;
import com.example.restaurantbackendapplication1.model.dto.MenuTypeDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuTypeEntity;
import com.example.restaurantbackendapplication1.model.enums.MenuTypeSortField;
import com.example.restaurantbackendapplication1.model.mapper.MenuTypeMapper;
import com.example.restaurantbackendapplication1.model.projection.MenuTypeFullSummary;
import com.example.restaurantbackendapplication1.model.projection.MenuTypeSummary;
import com.example.restaurantbackendapplication1.model.projection.MenuTypeWithCategoriesSummary;
import com.example.restaurantbackendapplication1.repository.MenuTypeRepository;
import com.example.restaurantbackendapplication1.service.MenuTypeService;
import com.example.restaurantbackendapplication1.utils.Pagination;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class MenuTypeServiceImpl implements MenuTypeService {

    private static final Set<String> ALLOWED_SORT_FIELDS = MenuTypeSortField.allowedFields();

    private final MenuTypeRepository menuTypeRepository;

    public MenuTypeServiceImpl(MenuTypeRepository menuTypeRepository) {
        this.menuTypeRepository = menuTypeRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateMenuTypeRequest request, Map<Long, LocaleEntity> localeEntityMap) {
        MenuTypeEntity entity = MenuTypeMapper.fromRequest(request, localeEntityMap);
        menuTypeRepository.save(entity);
        log.info("Menu created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public MenuTypeEntity getEntityById(Long id) {
        return menuTypeRepository.findByIdAndIsActiveAndIsDeleted(id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found with id: " + id));
    }

    @Override
    public MenuTypeResponse getById(Long id) {
        MenuTypeEntity entity = getEntityById(id);
        MenuTypeDto dto = MenuTypeMapper.toDto(entity);
        return new MenuTypeResponse(dto);
    }

    @Override
    public PaginatedResponse<MenuTypeSummary> getAll(PaginatedRequest request) {
        Page<MenuTypeSummary> page = menuTypeRepository.findAllByIsActiveAndIsDeleted(
                true, false, request.toPageable(ALLOWED_SORT_FIELDS), MenuTypeSummary.class);
        return Pagination.buildPaginatedResponse(page);
    }

    @Override
    public PaginatedResponse<MenuTypeWithCategoriesSummary> getAllWithCategories(PaginatedRequest request) {
        Page<MenuTypeWithCategoriesSummary> page = menuTypeRepository.findAllByIsActiveAndIsDeleted(
                true, false, request.toPageable(ALLOWED_SORT_FIELDS), MenuTypeWithCategoriesSummary.class);
        return Pagination.buildPaginatedResponse(page);
    }

    @Override
    public PaginatedResponse<MenuTypeFullSummary> getAllFull(PaginatedRequest request) {
        Page<MenuTypeFullSummary> page = menuTypeRepository.findAllByIsActiveAndIsDeleted(
                true, false, request.toPageable(ALLOWED_SORT_FIELDS), MenuTypeFullSummary.class);
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(MenuTypeEntity entity, UpdateMenuTypeRequest request) {
        MenuTypeMapper.update(entity, request);
        menuTypeRepository.save(entity);
        log.info("Menu updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(MenuTypeEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        menuTypeRepository.save(entity);
        log.info("Menu soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
