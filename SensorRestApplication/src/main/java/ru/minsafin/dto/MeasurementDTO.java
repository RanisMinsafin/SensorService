package ru.minsafin.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class MeasurementDTO {
    @Min(value = -100)
    @Max(value = 100)
    private Double value;
    private Boolean raining;
    private SensorDTO sensor;
}