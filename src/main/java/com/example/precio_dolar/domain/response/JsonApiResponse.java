package com.example.precio_dolar.domain.response;

import java.util.List;

public class JsonApiResponse<T> {
    private final List<JsonApiData<T>> data;

    public JsonApiResponse(List<JsonApiData<T>> data) {
        this.data = data;
    }

    public List<JsonApiData<T>> getData() {
        return data;
    }
}
