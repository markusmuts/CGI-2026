package com.cgi.backend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cgi.backend.dto.AvailableTimesDTO;
import com.cgi.backend.entity.Table;
import com.cgi.backend.repository.TableRepository;
import com.cgi.backend.service.TimeAvailabilityService;

@RestController
@RequestMapping("/api/tables")
@CrossOrigin(origins = "http://localhost:5173")
public class TableController {

    private final TableRepository tableRepository;
    private final TimeAvailabilityService timeAvailabilityService;

    public TableController(TableRepository tableRepository, TimeAvailabilityService timeAvailabilityService) {
        this.tableRepository = tableRepository;
        this.timeAvailabilityService = timeAvailabilityService;
    }

    @GetMapping
    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }

    @GetMapping("/{id}")
    public Table getTableById(@PathVariable Long id) {
        return tableRepository.findById(id).orElse(null);
    }

    @GetMapping("/available-times")
    public AvailableTimesDTO getAvailableTimes(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam int guests,
        @RequestParam(required = false) String location
    ) {
        return timeAvailabilityService.getAvailableTimes(date, guests, location);
    }
}
