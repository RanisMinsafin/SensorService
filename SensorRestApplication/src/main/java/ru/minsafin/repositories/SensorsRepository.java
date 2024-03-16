package ru.minsafin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.minsafin.models.Sensor;
import ru.minsafin.services.SensorsService;

import java.util.Optional;

public interface SensorsRepository extends JpaRepository<Sensor, Long> {
    public Optional<Sensor> findByName(String name);
}
