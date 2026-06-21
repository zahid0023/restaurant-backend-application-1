package com.example.restaurantbackendapplication1.restaurant.serviceImpl;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantclosedday.CreateRestaurantClosedDayRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantoperatinghour.CreateRestaurantOperatingHourRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.request.schedule.ScheduleRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.response.RestaurantScheduleResponse;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantClosedDayEntity;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantOperatingHourEntity;
import com.example.restaurantbackendapplication1.restaurant.model.mapper.RestaurantClosedDayMapper;
import com.example.restaurantbackendapplication1.restaurant.model.mapper.RestaurantOperatingHourMapper;
import com.example.restaurantbackendapplication1.restaurant.model.projection.RestaurantClosedDaySummary;
import com.example.restaurantbackendapplication1.restaurant.model.projection.RestaurantOperatingHourSummary;
import com.example.restaurantbackendapplication1.restaurant.repository.RestaurantClosedDayRepository;
import com.example.restaurantbackendapplication1.restaurant.repository.RestaurantOperatingHourRepository;
import com.example.restaurantbackendapplication1.restaurant.service.RestaurantOperatingHourService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RestaurantOperatingHourServiceImpl implements RestaurantOperatingHourService {

    private final RestaurantOperatingHourRepository operatingHourRepository;
    private final RestaurantClosedDayRepository closedDayRepository;

    public RestaurantOperatingHourServiceImpl(RestaurantOperatingHourRepository operatingHourRepository,
                                              RestaurantClosedDayRepository closedDayRepository) {
        this.operatingHourRepository = operatingHourRepository;
        this.closedDayRepository = closedDayRepository;
    }

    // ── Validation ────────────────────────────────────────────────────────────

    private void validateRequest(ScheduleRequest request) {
        // Single pass over closing — build set and catch duplicates immediately
        Set<DayOfWeek> closingDays = new HashSet<>();
        for (CreateRestaurantClosedDayRequest c : request.getClosing()) {
            if (!closingDays.add(c.getDayOfWeek())) {
                throw new IllegalArgumentException("Duplicate day in closing list: " + c.getDayOfWeek());
            }
        }

        // Single pass over operating — closing conflict + duplicate slot + time logic all at once
        Set<String> seenSlots = new HashSet<>();
        for (CreateRestaurantOperatingHourRequest slot : request.getOperating()) {
            DayOfWeek day = slot.getDayOfWeek();

            if (closingDays.contains(day)) {
                throw new IllegalArgumentException(
                        day + " is marked as closed — cannot add operating hours for it");
            }
            if (!seenSlots.add(day + ":" + slot.getSequenceNo())) {
                throw new IllegalArgumentException(
                        "Duplicate slot: " + day + " sequence_no=" + slot.getSequenceNo());
            }

            validateSlotTimes(slot, day);
        }
    }

    private void validateSlotTimes(CreateRestaurantOperatingHourRequest slot, DayOfWeek day) {
        boolean overnight = Boolean.TRUE.equals(slot.getClosesNextDay());
        boolean isClosed = Boolean.TRUE.equals(slot.getIsClosed());

        if (slot.getOpenTime().equals(slot.getCloseTime())) {
            throw new IllegalArgumentException(
                    "open_time and close_time cannot be the same: " + day + " seq=" + slot.getSequenceNo());
        }
        if (overnight && isClosed) {
            throw new IllegalArgumentException(
                    "A break period cannot also be closes_next_day: " + day + " seq=" + slot.getSequenceNo());
        }
        if (!overnight && slot.getCloseTime().isBefore(slot.getOpenTime())) {
            throw new IllegalArgumentException(
                    "close_time must be after open_time (use closes_next_day=true for overnight): "
                            + day + " seq=" + slot.getSequenceNo());
        }
    }

    // ── Persistence ───────────────────────────────────────────────────────────

    private void persistSchedule(ScheduleRequest request) {
        if (!request.getClosing().isEmpty()) {
            List<RestaurantClosedDayEntity> closedDays = request.getClosing().stream()
                    .map(RestaurantClosedDayMapper::create)
                    .toList();
            closedDayRepository.saveAll(closedDays);
        }

        if (!request.getOperating().isEmpty()) {
            List<RestaurantOperatingHourEntity> slots = request.getOperating().stream()
                    .map(RestaurantOperatingHourMapper::create)
                    .toList();
            operatingHourRepository.saveAll(slots);
        }
    }

    // ── Service methods ───────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    @Override
    public RestaurantScheduleResponse getSchedule() {
        List<RestaurantClosedDaySummary> closing =
                closedDayRepository.findAllByIsActiveAndIsDeleted(true, false);

        List<RestaurantOperatingHourSummary> allSlots =
                operatingHourRepository.findAllByIsActiveAndIsDeletedOrderBySequenceNoAsc(true, false);

        Map<DayOfWeek, List<RestaurantOperatingHourSummary>> operating = allSlots.stream()
                .collect(Collectors.groupingBy(
                        RestaurantOperatingHourSummary::getDayOfWeek,
                        () -> new TreeMap<>(Comparator.comparingInt(DayOfWeek::getValue)),
                        Collectors.toList()
                ));

        return RestaurantScheduleResponse.builder()
                .closing(closing)
                .operating(operating)
                .build();
    }

    @Transactional
    @Override
    public SuccessResponse createSchedule(ScheduleRequest request) {
        if (operatingHourRepository.existsByIsDeleted(false) || closedDayRepository.existsByIsDeleted(false)) {
            throw new IllegalStateException("Schedule already exists. Use PUT /schedule to replace it.");
        }
        validateRequest(request);
        persistSchedule(request);
        log.info("Schedule created: {} operating slots, {} closed days",
                request.getOperating().size(), request.getClosing().size());
        return new SuccessResponse(true, null);
    }

    @Transactional
    @Override
    public SuccessResponse updateSchedule(ScheduleRequest request) {
        validateRequest(request);
        operatingHourRepository.softDeleteAll();
        closedDayRepository.softDeleteAll();
        persistSchedule(request);
        log.info("Schedule replaced: {} operating slots, {} closed days",
                request.getOperating().size(), request.getClosing().size());
        return new SuccessResponse(true, null);
    }
}
