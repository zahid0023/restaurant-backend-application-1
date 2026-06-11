package com.example.restaurantbackendapplication1.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menutypelocale.CreateMenuLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menutypelocale.UpdateMenuLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuTypeLocaleEntity;
import com.example.restaurantbackendapplication1.model.mapper.MenuTypeLocaleMapper;
import com.example.restaurantbackendapplication1.repository.MenuTypeLocaleRepository;
import com.example.restaurantbackendapplication1.service.MenuTypeLocaleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class MenuTypeLocaleServiceImpl implements MenuTypeLocaleService {

    private final MenuTypeLocaleRepository menuTypeLocaleRepository;

    public MenuTypeLocaleServiceImpl(MenuTypeLocaleRepository menuTypeLocaleRepository) {
        this.menuTypeLocaleRepository = menuTypeLocaleRepository;
    }

    @Transactional
    @Override
    public SuccessResponse create(MenuTypeEntity menuTypeEntity,
                                  LocaleEntity localeEntity,
                                  CreateMenuLocaleRequest request) {
        MenuTypeLocaleEntity entity = MenuTypeLocaleMapper.fromRequest(request, menuTypeEntity, localeEntity);
        menuTypeLocaleRepository.save(entity);
        log.info("MenuLocale created with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Override
    public MenuTypeLocaleEntity getEntityById(Long id, MenuTypeEntity menuTypeEntity) {
        return menuTypeLocaleRepository.findByIdAndMenuTypeEntityAndIsActiveAndIsDeleted(id, menuTypeEntity, true, false)
                .orElseThrow(() -> new EntityNotFoundException("MenuLocale not found with id: " + id));
    }

    @Transactional
    @Override
    public SuccessResponse update(MenuTypeLocaleEntity entity,
                                  UpdateMenuLocaleRequest request) {
        MenuTypeLocaleMapper.update(entity, request);
        menuTypeLocaleRepository.save(entity);
        log.info("MenuLocale updated with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }

    @Transactional
    @Override
    public SuccessResponse delete(MenuTypeLocaleEntity entity) {
        entity.setIsDeleted(true);
        entity.setIsActive(false);
        menuTypeLocaleRepository.save(entity);
        log.info("MenuLocale soft-deleted with id: {}", entity.getId());
        return new SuccessResponse(true, entity.getId());
    }
}
