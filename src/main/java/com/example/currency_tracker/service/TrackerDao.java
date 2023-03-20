package com.example.currency_tracker.service;

import com.example.currency_tracker.model.dto.Tracking;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrackerDao {

    private final JdbcTemplate jdbcTemplate;

    public List<Tracking> getAllCurrencies() {
        final String query = "select * from tracking";
        return jdbcTemplate.query(query, new DataClassRowMapper<>(Tracking.class));
    }

    public void insertTracking(Tracking tracking) {
        final String query = "insert into tracking (currency_figi, currency_name) values (?, ?)";
        jdbcTemplate.update(query, tracking.currencyFigi(), tracking.currencyName());
    }

    public void deleteTracking(Tracking tracking) {
        final String query = "delete from tracking where currency_figi=?";
        jdbcTemplate.update(query, tracking.currencyFigi());
    }

}
