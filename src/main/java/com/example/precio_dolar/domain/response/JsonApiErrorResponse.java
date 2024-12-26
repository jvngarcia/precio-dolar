package com.example.precio_dolar.domain.response;

import java.util.List;

public class JsonApiErrorResponse {
    private final List<JsonApiError> errors;

    public JsonApiErrorResponse(List<JsonApiError> errors) {
        this.errors = errors;
    }

    public List<JsonApiError> getErrors() {
        return errors;
    }

}
