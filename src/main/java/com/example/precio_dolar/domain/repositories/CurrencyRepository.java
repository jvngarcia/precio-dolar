package com.example.precio_dolar.domain.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.precio_dolar.domain.dtos.CurrencyDTO;

@Repository
public interface CurrencyRepository {

    public List<CurrencyDTO> getData();

    public boolean saveData(List<CurrencyDTO> data);
}
