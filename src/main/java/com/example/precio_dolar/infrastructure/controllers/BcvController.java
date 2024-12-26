package com.example.precio_dolar.infrastructure.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.example.precio_dolar.domain.exceptions.HTTPException;
import com.example.precio_dolar.domain.response.JsonApiData;
import com.example.precio_dolar.domain.response.JsonApiError;
import com.example.precio_dolar.domain.response.JsonApiErrorResponse;
import com.example.precio_dolar.domain.response.JsonApiResponse;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.precio_dolar.application.GetRates;
import com.example.precio_dolar.domain.dtos.CurrencyDTO;

@RestController
@RequestMapping("/v1/bcv")
public class BcvController {
    @Autowired
    GetRates service;

    @GetMapping()
    public ResponseEntity<?> getDolar() {
        Either<HTTPException, List<CurrencyDTO>> result = service.execute();

        return result.fold(
                error -> ResponseEntity.status(error.getStatusCode()).body(
                    new JsonApiErrorResponse(List.of(
                        new JsonApiError(
                            String.valueOf(error.getStatusCode()),
                            error.getTitle(),
                            error.getMessage()
                        )
                    ))
                ),
                data -> ResponseEntity.ok(new JsonApiResponse<>(data.stream().map(
                                currencyDTO -> new JsonApiData<>("currencies", null, currencyDTO)
                        ).collect(Collectors.toList()))
                )
        );
    }
}
