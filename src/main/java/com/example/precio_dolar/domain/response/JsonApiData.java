package com.example.precio_dolar.domain.response;

import com.example.precio_dolar.domain.dtos.CurrencyDTO;

public class JsonApiData<T> {
    private final String type;
    private final String id;
    private final T attributes;

    public JsonApiData(String type, String id, T attributes) {
        this.type = type;
        this.id = id;
        this.attributes = attributes;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public T getAttributes() {
        return attributes;
    }

}
