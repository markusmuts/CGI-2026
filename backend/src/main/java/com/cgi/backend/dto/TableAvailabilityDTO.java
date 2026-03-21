package com.cgi.backend.dto;

import java.util.List;

public record TableAvailabilityDTO(
    String date,
    String time,
    int guests,
    String location,
    int reservationDurationMinutes,
    List<TableAvailabilityItemDTO> tables
) {
}
