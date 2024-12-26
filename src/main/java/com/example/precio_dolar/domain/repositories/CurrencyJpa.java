package com.example.precio_dolar.domain.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.precio_dolar.domain.entities.Currency;


public interface CurrencyJpa extends JpaRepository<Currency, Long> {
    List<Currency> findByCreatedAtBetween(LocalDateTime startTime, LocalDateTime endTime);
}