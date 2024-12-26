package com.example.precio_dolar.domain.exceptions;

public class CurrencySaveException extends HTTPException{
    public CurrencySaveException() {
        super("Error when trying to save currency", "Currency Save Failure", 500);
    }
}
