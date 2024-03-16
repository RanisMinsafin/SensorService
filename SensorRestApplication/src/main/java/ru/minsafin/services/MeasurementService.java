package ru.minsafin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.minsafin.models.Measurement;
import ru.minsafin.repositories.MeasurementRepository;
import ru.minsafin.util.MeasurementNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private MeasurementRepository measurementRepository;
    private SensorsService sensorsService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorsService sensorsService) {
        this.measurementRepository = measurementRepository;
        this.sensorsService = sensorsService;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    @Transactional
    public void add(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }

    public Long getRainyDaysCount(){
       return measurementRepository.countRainyDays();
    }

    private void enrichMeasurement(Measurement measurement) {
        measurement.setDateTime(LocalDateTime.now());
        measurement.setSensor(sensorsService.findByName(measurement.getSensor().getName()).get());
    }
}
