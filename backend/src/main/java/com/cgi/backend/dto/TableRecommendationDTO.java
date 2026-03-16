package com.cgi.backend.dto;

import com.cgi.backend.entity.Table;

public class TableRecommendationDTO {

    private final Table table;
    private final double score;

    public TableRecommendationDTO(Table table, double score) {
        this.table = table;
        this.score = score;
    }

    public Table getTable() {
        return table;
    }

    public double getScore() {
        return score;
    }
}