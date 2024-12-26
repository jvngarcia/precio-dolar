package com.example.precio_dolar.domain.exceptions;

public class ScrappingFailureException extends HTTPException{
    public ScrappingFailureException() {
        super("Error when trying to obtain data", "Scrapping Failure", 500);
    }
}
