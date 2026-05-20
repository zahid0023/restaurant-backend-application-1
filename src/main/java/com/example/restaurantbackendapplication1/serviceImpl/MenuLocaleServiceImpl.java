package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menulocale.CreateMenuLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menulocale.UpdateMenuLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuLocaleEntity;
import com.example.restaurantbackendapplication1.model.mapper.MenuLocaleMapper;
import com.example.restaurantbackendapplication1.repository.MenuLocaleRepository;
import com.example.restaurantbackendapplication1.service.MenuLocaleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class MenuLocaleServiceImpl implements MenuLocaleService {

    private final MenuLocaleRepository menuLocaleRepository;

    public MenuLocaleServiceImpl(MenuLocaleRepository menuLocaleRepository) {
        this.menuLocaleRepository = menuLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(MenuEntity menuEntity, LocaleEntity localeEntity, CreateMenuLocaleRequest request) {
        MenuLocaleEntity entity = MenuLocaleMapper.fromRequest(request, menuEntity, localeEntity);
        menuLocaleRepository.save(entity);
        log.info("MenuLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public MenuLocaleEntity getEntityById(Long id, MenuEntity menuEntity) {
        return menuLocaleRepository.findByIdAndMenuEntityAndIsActiveAndIsDeleted(id, menuEntity, true, false)
                .orElseThrow(() -> new EntityNotFoundException("MenuLocale not found with id: " + id));
    }

    @Transactional
    @Override
    public SuccessResponse update(MenuLocaleEntity entity, UpdateMenuLocaleRequest request) {
        MenuLocaleMapper.update(entity, request);
        menuLocaleRepository.save(entity);
        log.info("MenuLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(MenuLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        menuLocaleRepository.save(entity);
        log.info("MenuLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
