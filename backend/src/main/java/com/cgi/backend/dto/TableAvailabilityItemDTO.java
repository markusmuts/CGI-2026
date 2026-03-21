package com.cgi.backend.dto;

import java.util.Set;

public record TableAvailabilityItemDTO(
    Long id,
    int tableNumber,
    int capacity,
    Set<String> features,
    boolean available,
    boolean recommended,
    double score
) {
}
