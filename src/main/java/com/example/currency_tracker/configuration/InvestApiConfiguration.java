package com.example.currency_tracker.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.piapi.core.InstrumentsService;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.MarketDataService;

@Configuration
public class InvestApiConfiguration {

    @Bean
    public InvestApi investApi(@Value("${invest-api-token}") String token) {
        return InvestApi.create(token);
    }

    @Bean
    public InstrumentsService instrumentsService(InvestApi investApi) {
        return investApi.getInstrumentsService();
    }

    @Bean
    public MarketDataService marketDataService(InvestApi investApi) {
        return investApi.getMarketDataService();
    }

}
