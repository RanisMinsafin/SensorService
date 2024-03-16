package ru.minsafin.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.minsafin.dto.SensorDTO;
import ru.minsafin.models.Sensor;
import ru.minsafin.services.SensorsService;
import ru.minsafin.util.EntityErrorResponse;
import ru.minsafin.util.SensorNotCreatedException;
import ru.minsafin.util.SensorNotFoundException;
import ru.minsafin.util.SensorValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private SensorsService sensorsService;
    private SensorValidator sensorValidator;
    private ModelMapper modelMapper;

    @Autowired
    public SensorsController(SensorsService sensorsService, SensorValidator sensorValidator, ModelMapper modelMapper) {
        this.sensorsService = sensorsService;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public SensorDTO getSensor(@PathVariable("id") int id) {
        return convertToSensorDTO(sensorsService.findOne(id));
    }

    @GetMapping()
    public List<SensorDTO> getSensors() {
        return sensorsService.findAll()
                .stream()
                .map(this::convertToSensorDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO,
                                                   BindingResult bindingResult) {
        Sensor sensorToAdd = convertToSensor(sensorDTO);
        sensorValidator.validate(sensorToAdd, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new SensorNotCreatedException("Sensor with this name is already exist");
        }

        sensorsService.register(convertToSensor(sensorDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    @ExceptionHandler
    private ResponseEntity<EntityErrorResponse> handleException(SensorNotFoundException e) {
        EntityErrorResponse response = new EntityErrorResponse(
                "Sensor with this id was not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<EntityErrorResponse> handleException(SensorNotCreatedException e) {
        EntityErrorResponse response = new EntityErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
