package ru.minsafin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.minsafin.models.Sensor;
import ru.minsafin.repositories.SensorsRepository;
import ru.minsafin.util.SensorNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {
    private SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public List<Sensor> findAll() {
        return sensorsRepository.findAll();
    }

    public Sensor findOne(int id) {
        Optional<Sensor> foundSensor = sensorsRepository.findById((long) id);
        return foundSensor.orElseThrow(SensorNotFoundException::new);
    }

    public Optional<Sensor> findByName(String name) {
        return sensorsRepository.findByName(name);
    }

    @Transactional
    public void register(Sensor sensor) {
        sensorsRepository.save(sensor);
    }
}
