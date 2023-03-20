package com.example.currency_tracker.service.invest;

import com.example.currency_tracker.model.dto.CurrencyInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.Currency;
import ru.tinkoff.piapi.contract.v1.InstrumentStatus;
import ru.tinkoff.piapi.core.InstrumentsService;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InstrumentServiceAdapter {

    private final InstrumentsService instrumentsService;

    public Map<String, CurrencyInfo> getCurrenciesInfoMap() {
        return instrumentsService.getCurrenciesSync(InstrumentStatus.INSTRUMENT_STATUS_BASE).stream()
                .collect(Collectors.toMap(Currency::getName, currency -> new CurrencyInfo(currency.getFigi(), currency.getLot(), currency.getNominal().getUnits())));
    }

}
