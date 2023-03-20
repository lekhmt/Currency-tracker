package com.example.currency_tracker.logic;

import com.example.currency_tracker.model.dto.Tracking;
import com.example.currency_tracker.model.dto.CurrencyInfo;
import com.example.currency_tracker.model.dto.CurrencyPrice;
import com.example.currency_tracker.service.TrackerDao;
import com.example.currency_tracker.service.invest.InstrumentServiceAdapter;
import com.example.currency_tracker.service.invest.MarketDataServiceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
public class Subscriptions {

    private Map<String, CurrencyInfo> cache = new HashMap<>();
    private final InstrumentServiceAdapter instrumentServiceAdapter;

    private final MarketDataServiceAdapter marketDataServiceAdapter;

    private final TrackerDao trackerDao;

    @Scheduled(cron = "0 0 * * * *")
    public void logCurrenciesPrices() {
        var currenciesPricesList = getPrices();
        for (var currencyPrice : currenciesPricesList) {
            System.out.println(currencyPrice.currencyName() +
                    ": lot = " + currencyPrice.lotPrice() +
                    ", nominal = " + currencyPrice.nominal() +
                    ", lotPrice = " + currencyPrice.lotPrice() +
                    ", finalPrice = " + currencyPrice.finalPrice());
        }
    }

    public List<CurrencyPrice> getPrices() {
        var currenciesList = trackerDao.getAllCurrencies().stream().map(Tracking::currencyName).toList();
        List<CurrencyPrice> response = new ArrayList<>();
        for (var currencyName : currenciesList) {
            var currencyFigi = getCurrencyFigiByCurrencyName(currencyName);
            var lot = getCurrencyLotByCurrencyName(currencyName);
            var nominal = getCurrencyNominalByCurrencyName(currencyName);
            var lotPrice = marketDataServiceAdapter.getCurrencyPriceByFigi(currencyFigi);
            response.add(new CurrencyPrice(currencyName, lot, nominal, lotPrice, lot * lotPrice / nominal));
        }
        return response;
    }

    public void subscribe(String currencyName) {
        var currencyFigi = getCurrencyFigiByCurrencyName(currencyName);
        var tracking = new Tracking(currencyFigi, currencyName);
        trackerDao.insertTracking(tracking);
    }

    public void unsubscribe(String currencyName) {
        var currencyFigi = getCurrencyFigiByCurrencyName(currencyName);
        var tracking = new Tracking(currencyFigi, currencyName);
        trackerDao.deleteTracking(tracking);
    }

    private String getCurrencyFigiByCurrencyName(String currencyName) {
        if (!cache.containsKey(currencyName)) {
            updateCache();
        }
        return ofNullable(cache.get(currencyName)).map(CurrencyInfo::figi).orElseThrow();
    }

    private Integer getCurrencyLotByCurrencyName(String currencyName) {
        if (!cache.containsKey(currencyName)) {
            updateCache();
        }
        return ofNullable(cache.get(currencyName)).map(CurrencyInfo::lot).orElseThrow();
    }

    private Long getCurrencyNominalByCurrencyName(String currencyName) {
        if (!cache.containsKey(currencyName)) {
            updateCache();
        }
        return ofNullable(cache.get(currencyName)).map(CurrencyInfo::nominal).orElseThrow();
    }

    private void updateCache() {
        cache = instrumentServiceAdapter.getCurrenciesInfoMap();
    }

}
