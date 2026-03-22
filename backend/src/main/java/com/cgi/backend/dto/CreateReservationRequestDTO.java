package com.cgi.backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationRequestDTO(
    Long tableId,
    LocalDate date,
    LocalTime time,
    int guestCount,
    String customerName,
    String customerEmail,
    String customerPhoneNumber,
    String additionalInfo
) {
}
