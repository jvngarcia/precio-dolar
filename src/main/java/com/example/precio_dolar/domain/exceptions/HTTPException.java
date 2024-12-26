package com.example.precio_dolar.domain.exceptions;

public class HTTPException extends Exception{

    protected int statusCode;
    protected String title;

    public HTTPException(String message, String title, int statusCode) {
        super(message);
        this.statusCode = statusCode;
        this.title = title;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return super.getMessage();
    }

    public String getTitle() {
        return title;
    }
}
