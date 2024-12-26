package com.example.precio_dolar.application;

import java.util.List;

import com.example.precio_dolar.domain.exceptions.CurrencySaveException;
import com.example.precio_dolar.domain.exceptions.HTTPException;
import com.example.precio_dolar.domain.exceptions.ScrappingFailureException;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.precio_dolar.domain.dtos.CurrencyDTO;
import com.example.precio_dolar.domain.repositories.CurrencyRepository;
import com.example.precio_dolar.domain.repositories.WebScrapping;

@Service
public class GetRates {

    @Autowired
    CurrencyRepository repository;

    @Autowired
    WebScrapping webScrapping;

    public Either<HTTPException, List<CurrencyDTO>> execute() {
        List<CurrencyDTO> currencyList = repository.getData();

        if (!currencyList.isEmpty()) {
            return Either.right(currencyList);
        }

        List<CurrencyDTO> data = webScrapping.execute();

        if (data.isEmpty()) {
            return Either.left(new ScrappingFailureException());
        }

        boolean saved = repository.saveData(data);

        if (!saved) {
            return Either.left(new CurrencySaveException());
        }

        return Either.right(data);
    }

}
