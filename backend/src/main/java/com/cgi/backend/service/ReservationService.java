package com.cgi.backend.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.cgi.backend.dto.TableRecommendationDTO;
import com.cgi.backend.entity.Table;
import com.cgi.backend.repository.ReservationRepository;
import com.cgi.backend.repository.TableRepository;

@Service
public class ReservationService {

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
}