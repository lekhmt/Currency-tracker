package com.example.currency_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CurrencyTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyTrackerApplication.class, args);
    }

}
