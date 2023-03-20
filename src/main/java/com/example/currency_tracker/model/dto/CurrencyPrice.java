package com.example.currency_tracker.model.dto;

public record CurrencyPrice(
        String currencyName,

        Integer lot,
        Long nominal,
        Long lotPrice,
        Long finalPrice
) {
}
