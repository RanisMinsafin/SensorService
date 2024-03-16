package ru.minsafin.dto;

import lombok.Data;

import java.util.List;

@Data
public class MeasurementResponse {
    private final List<MeasurementDTO> measurements;

    public MeasurementResponse(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }
}
