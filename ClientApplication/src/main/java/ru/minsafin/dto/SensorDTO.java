package ru.minsafin.dto;


import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class SensorDTO {
    @NotEmpty(message = "Name should not be empty")
    String name;
}
