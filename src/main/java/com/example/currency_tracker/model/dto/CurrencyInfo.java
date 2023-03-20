package com.example.currency_tracker.model.dto;

public record CurrencyInfo(
    String figi,
    Integer lot,
    Long nominal
) {}
