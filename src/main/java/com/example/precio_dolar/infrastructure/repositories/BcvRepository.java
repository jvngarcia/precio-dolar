package com.example.precio_dolar.infrastructure.repositories;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.precio_dolar.domain.dtos.CurrencyDTO;
import com.example.precio_dolar.domain.entities.Currency;
import com.example.precio_dolar.domain.repositories.CurrencyJpa;
import com.example.precio_dolar.domain.repositories.CurrencyRepository;

@Repository
public class BcvRepository implements CurrencyRepository {

    @Autowired
    CurrencyJpa currencyJPA;


    @Override
    public List<CurrencyDTO> getData() {

        List<CurrencyDTO> currencyList = new ArrayList<CurrencyDTO>();

        // Obtener la hora actual
        LocalDateTime now = LocalDateTime.now();
        // Obtener el inicio de la hora actual (00 minutos, 00 segundos)
        LocalDateTime startOfHour = now.truncatedTo(ChronoUnit.HOURS);
        // Obtener el final de la hora actual (59 minutos, 59 segundos)
        LocalDateTime endOfHour = startOfHour.plusMinutes(59).plusSeconds(59);

        currencyJPA.findByCreatedAtBetween(startOfHour, endOfHour).forEach(currency -> {
            currencyList.add(new CurrencyDTO(currency.getCurrency(), currency.getValue()));
        });

        return currencyList;
    }

    @Override
    public boolean saveData(List<CurrencyDTO> data) {

        try {
            data.forEach(currency -> {
                currencyJPA.save(new Currency(currency.currency(), currency.value()));
            });
        } catch (Exception e) {
            System.err.println("Error al guardar los datos en la base de datos");
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
