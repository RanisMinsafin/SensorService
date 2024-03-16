package ru.minsafin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.minsafin.models.Measurement;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    @Query("SELECT COUNT(m) FROM Measurement m WHERE m.raining = true")
    Long countRainyDays();
}
