package ru.minsafin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.minsafin.models.Measurement;
import ru.minsafin.repositories.MeasurementRepository;
import ru.minsafin.services.SensorsService;

@Component
public class MeasurementValidator implements Validator {

    private MeasurementRepository measurementRepository;

    private SensorsService sensorsService;

    @Autowired
    public MeasurementValidator(MeasurementRepository measurementRepository, SensorsService sensorsService) {
        this.measurementRepository = measurementRepository;
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Measurement measurement = (Measurement) o;
        if(measurement.getSensor()== null){
            return;
        }
        if(sensorsService.findByName(measurement.getSensor().getName()).isEmpty()){
            errors.rejectValue("sensor", "Sensor with this name does not exist");
        }
    }
}
