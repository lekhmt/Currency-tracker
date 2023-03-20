package com.example.currency_tracker.service.invest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.core.MarketDataService;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class MarketDataServiceAdapter {

    private final MarketDataService marketDataService;

    public long getCurrencyPriceByFigi(String currencyFigi) {
        try {
            var response = marketDataService.getLastPrices(Collections.singleton(currencyFigi)).get();
            return response.get(0).getPrice().getUnits();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
