package com.cgi.backend.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.cgi.backend.dto.CreateReservationRequestDTO;
import com.cgi.backend.dto.ReservationResponseDTO;
import com.cgi.backend.dto.TableRecommendationDTO;
import com.cgi.backend.entity.Reservation;
import com.cgi.backend.entity.Table;
import com.cgi.backend.repository.ReservationRepository;
import com.cgi.backend.repository.TableRepository;

@Service
public class ReservationService {

    private static final int MORNING_LUNCH_DURATION_MINUTES = 90;
    private static final int EVENING_DURATION_MINUTES = 150;

    private final TableRepository tableRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(TableRepository tableRepository, ReservationRepository reservationRepository) {
        this.tableRepository = tableRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<TableRecommendationDTO> getRecommendations(
        LocalDateTime time,
        int guests,
        List<String> preferredFeatures
    ) {
        List<Table> allTables = tableRepository.findAll();
        List<Long> occupiedTableIds = reservationRepository.findOccupiedTableIds(time, time.plusHours(2));

        return allTables.stream()
            .filter(table -> !occupiedTableIds.contains(table.getId()))
            .filter(table -> table.getCapacity() >= guests)
            .map(table -> new TableRecommendationDTO(table, calculateScore(table, guests, preferredFeatures)))
            .sorted(Comparator.comparingDouble(TableRecommendationDTO::getScore).reversed())
            .toList();
    }

    private double calculateScore(Table table, int guests, List<String> preferences) {
        double score = 0;

        int extraSeats = table.getCapacity() - guests;
        score += Math.max(0, 10 - extraSeats);

        Set<String> tableFeatures = table.getFeatures();
        if (preferences == null || tableFeatures == null) {
            return score;
        }

        for (String preference : preferences) {
            if (tableFeatures.contains(preference)) {
                score += 5;
            }
        }

        return score;
    }

    public ReservationResponseDTO createReservation(CreateReservationRequestDTO request) {
        validateCreateRequest(request);

        Table table = tableRepository.findById(request.tableId())
            .orElseThrow(() -> new IllegalArgumentException("Selected table does not exist."));

        int normalizedGuestCount = Math.max(1, request.guestCount());
        if (normalizedGuestCount > table.getCapacity()) {
            throw new IllegalArgumentException("Guest count exceeds selected table capacity.");
        }

        LocalDate reservationDate = request.date();
        LocalTime reservationTime = request.time();
        LocalDateTime startTime = LocalDateTime.of(reservationDate, reservationTime);
        LocalDateTime endTime = startTime.plusMinutes(reservationDurationMinutes(startTime));

        if (startTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Reservation start time must be in the future.");
        }

        boolean overlapping = reservationRepository.existsOverlappingReservation(table.getId(), startTime, endTime);
        if (overlapping) {
            throw new IllegalStateException("Selected table is no longer available for this time.");
        }

        Reservation reservation = new Reservation(
            table,
            reservationDate,
            startTime,
            endTime,
            normalizedGuestCount,
            request.customerName().trim(),
            request.customerEmail().trim(),
            request.customerPhoneNumber().trim(),
            sanitizeOptionalInfo(request.additionalInfo())
        );

        Reservation saved = reservationRepository.save(reservation);
        return new ReservationResponseDTO(
            saved.getId(),
            table.getId(),
            table.getTableNumber(),
            saved.getReservationDate(),
            saved.getStartTime(),
            saved.getEndTime(),
            saved.getGuestCount(),
            saved.getCustomerName(),
            saved.getCustomerEmail(),
            saved.getCustomerPhoneNumber(),
            saved.getAdditionalInfo()
        );
    }

    private static void validateCreateRequest(CreateReservationRequestDTO request) {
        if (request == null) {
            throw new IllegalArgumentException("Reservation request is required.");
        }

        if (request.tableId() == null) {
            throw new IllegalArgumentException("Table id is required.");
        }

        if (request.date() == null) {
            throw new IllegalArgumentException("Reservation date is required.");
        }

        if (request.time() == null) {
            throw new IllegalArgumentException("Reservation time is required.");
        }

        if (request.guestCount() <= 0) {
            throw new IllegalArgumentException("Guest count must be at least 1.");
        }

        if (request.customerName() == null || request.customerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name is required.");
        }

        if (request.customerEmail() == null || request.customerEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer email is required.");
        }

        if (request.customerPhoneNumber() == null || request.customerPhoneNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer phone number is required.");
        }
    }

    private static String sanitizeOptionalInfo(String additionalInfo) {
        if (additionalInfo == null) {
            return "";
        }

        return additionalInfo.trim();
    }

    private static int reservationDurationMinutes(LocalDateTime startTime) {
        if (startTime.getHour() < 17) {
            return MORNING_LUNCH_DURATION_MINUTES;
        }

        return EVENING_DURATION_MINUTES;
    }
}