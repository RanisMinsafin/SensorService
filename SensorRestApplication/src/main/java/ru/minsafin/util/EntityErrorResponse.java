package ru.minsafin.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EntityErrorResponse {
    private String message;
    private long timestamp;

    public EntityErrorResponse() {
    }
}
