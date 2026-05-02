package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menusectionlocale.CreateMenuSectionLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menusectionlocale.UpdateMenuSectionLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.MenuSectionLocaleResponse;
import com.example.restaurantbackendapplication1.model.dto.MenuSectionLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionLocaleEntity;
import com.example.restaurantbackendapplication1.model.enums.MenuSectionLocaleSortField;
import com.example.restaurantbackendapplication1.model.mapper.MenuSectionLocaleMapper;
import com.example.restaurantbackendapplication1.model.projection.MenuSectionLocaleSummary;
import com.example.restaurantbackendapplication1.repository.MenuSectionLocaleRepository;
import com.example.restaurantbackendapplication1.service.MenuSectionLocaleService;
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
public class MenuSectionLocaleServiceImpl implements MenuSectionLocaleService {

    private static final Set<String> ALLOWED_SORT_FIELDS = MenuSectionLocaleSortField.allowedFields();

    private final MenuSectionLocaleRepository menuSectionLocaleRepository;

    public MenuSectionLocaleServiceImpl(MenuSectionLocaleRepository menuSectionLocaleRepository) {
        this.menuSectionLocaleRepository = menuSectionLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(MenuSectionEntity menuSectionEntity, LocaleEntity localeEntity,
                                  CreateMenuSectionLocaleRequest request) {
        MenuSectionLocaleEntity entity = MenuSectionLocaleMapper.fromRequest(request, menuSectionEntity, localeEntity);
        menuSectionLocaleRepository.save(entity);
        log.info("MenuSectionLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public MenuSectionLocaleEntity getEntityById(Long id, MenuSectionEntity menuSectionEntity) {
        return menuSectionLocaleRepository
                .findByIdAndMenuSectionEntityAndIsActiveAndIsDeleted(id, menuSectionEntity, true, false)
                .orElseThrow(() -> new EntityNotFoundException("MenuSectionLocale not found with id: " + id));
    }

    @Override
    public MenuSectionLocaleResponse getById(Long id, MenuSectionEntity menuSectionEntity) {
        MenuSectionLocaleEntity entity = getEntityById(id, menuSectionEntity);
        MenuSectionLocaleDto dto = MenuSectionLocaleMapper.toDto(entity);
        return new MenuSectionLocaleResponse(dto);
    }

    @Override
    public PaginatedResponse<MenuSectionLocaleSummary> getAll(MenuSectionEntity menuSectionEntity,
                                                              PaginatedRequest request) {
        Page<@NonNull MenuSectionLocaleSummary> page = menuSectionLocaleRepository
                .findAllByMenuSectionEntityAndIsActiveAndIsDeleted(
                        menuSectionEntity, true, false, request.toPageable(ALLOWED_SORT_FIELDS));
        return Pagination.buildPaginatedResponse(page);
    }

    @Transactional
    @Override
    public SuccessResponse update(MenuSectionLocaleEntity entity, LocaleEntity localeEntity,
                                  UpdateMenuSectionLocaleRequest request) {
        MenuSectionLocaleMapper.update(entity, request, localeEntity);
        menuSectionLocaleRepository.save(entity);
        log.info("MenuSectionLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(MenuSectionLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        menuSectionLocaleRepository.save(entity);
        log.info("MenuSectionLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
