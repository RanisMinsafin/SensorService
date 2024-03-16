package ru.minsafin.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.minsafin.dto.MeasurementDTO;
import ru.minsafin.dto.MeasurementResponse;
import ru.minsafin.models.Measurement;
import ru.minsafin.services.MeasurementService;
import ru.minsafin.util.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private MeasurementService measurementService;
    private ModelMapper modelMapper;
    private MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService,
                                 MeasurementValidator measurementValidator,
                                 ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.measurementValidator = measurementValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public MeasurementResponse getAll() {
        return new MeasurementResponse(
                measurementService.findAll()
                        .stream()
                        .map(this::convertToMeasurementDTO)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount() {
        return measurementService.getRainyDaysCount();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                          BindingResult bindingResult) {
        Measurement measurementToAdd = convertToMeasurement(measurementDTO);
        measurementValidator.validate(measurementToAdd, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new MeasurementNotCreatedException("Sensor with this name does not exist");
        }

        measurementService.add(convertToMeasurement(measurementDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    @ExceptionHandler
    private ResponseEntity<EntityErrorResponse> handleException(MeasurementNotFoundException e) {
        EntityErrorResponse response = new EntityErrorResponse(
                "Sensor with this name was not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<EntityErrorResponse> handleException(MeasurementNotCreatedException e) {
        EntityErrorResponse response = new EntityErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
