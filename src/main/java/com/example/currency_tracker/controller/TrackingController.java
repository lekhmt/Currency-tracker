package com.example.currency_tracker.controller;

import com.example.currency_tracker.logic.Subscriptions;
import com.example.currency_tracker.model.dto.CurrencyPrice;
import com.example.currency_tracker.model.dto.ResultReponse;
import com.example.currency_tracker.model.dto.SubscriptionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrackingController {

        private final Subscriptions subscriptions;

        @GetMapping("/currency")
        public ResponseEntity<List<CurrencyPrice>> getPrices() {
                var subscriptionsList = subscriptions.getPrices();
                return new ResponseEntity<>(subscriptionsList, HttpStatus.OK);
        }

        @PostMapping("/currency/subscription")
        public ResponseEntity<ResultReponse> subscribe(@RequestBody SubscriptionRequest subscribeRequest) {
                subscriptions.subscribe(subscribeRequest.currencyName());
                return new ResponseEntity<>(HttpStatus.OK);
        }

        @DeleteMapping("/currency/subscription")
        public ResponseEntity<ResultReponse> unsubscribe(@RequestBody SubscriptionRequest unsubscribeRequest) {
                subscriptions.unsubscribe(unsubscribeRequest.currencyName());
                return new ResponseEntity<>(HttpStatus.OK);
        }

}
