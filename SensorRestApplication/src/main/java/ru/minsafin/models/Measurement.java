package ru.minsafin.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;

import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

@Entity
@Table(schema = "rest", name = "measurement")
@Data
@AllArgsConstructor
public class Measurement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Min(value = -100)
    @Max(value = 100)
    @Column(name = "value")
    private Double value;

    @NotNull
    @Column(name = "raining")
    private Boolean raining;

    @NotNull
    @Column(name = "measurement_date_time")
    private LocalDateTime dateTime;

    @NotNull
    @ManyToOne
    @Cascade(SAVE_UPDATE)
    @JoinColumn(name = "sensor", referencedColumnName = "name")
    private Sensor sensor;

    public Measurement() {
    }
}
