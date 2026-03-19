package com.cgi.backend.dto;

import java.util.List;

public record AvailableTimesDTO(
    String date,
    int guests,
    String location,
    int reservationDurationMinutes,
    List<String> availableTimes
) {
}