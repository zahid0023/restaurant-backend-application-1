package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menu.CreateMenuRequest;
import com.example.restaurantbackendapplication1.dto.request.menu.UpdateMenuRequest;
import com.example.restaurantbackendapplication1.dto.response.MenuResponse;
import com.example.restaurantbackendapplication1.model.dto.MenuDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.enums.MenuSortField;
import com.example.restaurantbackendapplication1.model.mapper.MenuMapper;
import com.example.restaurantbackendapplication1.model.projection.MenuSummary;
import com.example.restaurantbackendapplication1.repository.MenuRepository;
import com.example.restaurantbackendapplication1.service.MenuService;
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
public class MenuServiceImpl implements MenuService {

    private static final Set<String> ALLOWED_SORT_FIELDS = MenuSortField.allowedFields();

    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(CreateMenuRequest request, Map<Long, LocaleEntity> localeEntityMap) {
        MenuEntity entity = MenuMapper.fromRequest(request, localeEntityMap);
        menuRepository.save(entity);
        log.info("Menu created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public MenuEntity getEntityById(Long id) {
        return menuRepository.findByIdAndIsActiveAndIsDeleted(id, true, false)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found with id: " + id));
    }

    @Override
    public MenuResponse getById(Long id) {
        MenuEntity entity = getEntityById(id);
        MenuDto dto = MenuMapper.toDto(entity);
        return new MenuResponse(dto);
    }

    @Override
    public PaginatedResponse<MenuSummary> getAll(PaginatedRequest request) {
        Page<@NonNull MenuSummary> page = menuRepository.findAllByIsActiveAndIsDeleted(
                true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(MenuEntity entity, UpdateMenuRequest request) {
        MenuMapper.update(entity, request);
        menuRepository.save(entity);
        log.info("Menu updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(MenuEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        menuRepository.save(entity);
        log.info("Menu soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
