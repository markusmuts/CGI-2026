package com.cgi.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservationResponseDTO(
    Long id,
    Long tableId,
    int tableNumber,
    LocalDate reservationDate,
    LocalDateTime startTime,
    LocalDateTime endTime,
    int guestCount,
    String customerName,
    String customerEmail,
    String customerPhoneNumber,
    String additionalInfo
) {
}
