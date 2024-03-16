package ru.minsafin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.minsafin.models.Sensor;
import ru.minsafin.services.SensorsService;

@Component
public class SensorValidator implements Validator {
    private SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Sensor sensor = (Sensor) o;
        if (sensorsService.findByName(sensor.getName()).isPresent()) {
            errors.rejectValue("name", "Sensor with that name already exists");
        }
    }
}
