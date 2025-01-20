package com.example.precio_dolar.infrastructure.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.example.precio_dolar.domain.exceptions.HTTPException;
import com.example.precio_dolar.domain.response.JsonApiData;
import com.example.precio_dolar.domain.response.JsonApiError;
import com.example.precio_dolar.domain.response.JsonApiErrorResponse;
import com.example.precio_dolar.domain.response.JsonApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.precio_dolar.application.GetRates;
import com.example.precio_dolar.domain.dtos.CurrencyDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@Tag(name = "BCV", description = "Operations related to the Central Bank of Venezuela")
@RestController
@RequestMapping("/v1/bcv")
public class BcvController {
    @Autowired
    GetRates service;

    @Operation(
            summary = "Get the current dollar price",
            description = "Get the current dollar price from the Central Bank of Venezuela"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The current dollar price",
                    content = @Content(
                            mediaType = "application/vnd.api+json",
                            schema = @Schema(implementation = JsonApiResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/vnd.api+json",
                            schema = @Schema(implementation = JsonApiErrorResponse.class)
                    )
            )
    })
    @GetMapping(value = "", produces = "application/vnd.api+json")
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
