package com.hms.service;

import com.hms.entity.cars.*;
import com.hms.entity.evaluation.DetailedCarEvaluation;
import com.hms.repository.*;
import org.springframework.stereotype.Service;

@Service
public class DetailedCarEvaluationService {

    private final DetailedCarEvaluationRepository evaluationRepository;
    private final FuelTypeRepository fuelTypeRepository;
    private final TransmissionRepository transmissionRepository;
    private final KmDrivenRepository kmDrivenRepository;
    private final CarRepository carRepository;
    private final YearRepository yearRepository;


    public DetailedCarEvaluationService(DetailedCarEvaluationRepository evaluationRepository,
                                        FuelTypeRepository fuelTypeRepository,
                                        TransmissionRepository transmissionRepository,
                                        KmDrivenRepository kmDrivenRepository, CarRepository carRepository, YearRepository yearRepository) {
        this.evaluationRepository = evaluationRepository;
        this.fuelTypeRepository = fuelTypeRepository;
        this.transmissionRepository = transmissionRepository;
        this.kmDrivenRepository = kmDrivenRepository;
        this.carRepository = carRepository;
        this.yearRepository = yearRepository;
    }

    public DetailedCarEvaluation saveEvaluation(
            Long yearId,
            Long fuelTypeId,
            Long transmissionId,
            Long kmDrivenId,
            Long carId
    ) {

        FuelType fuelType = fuelTypeRepository.findById(fuelTypeId)
                .orElseThrow(() -> new RuntimeException("FuelType not found"));
        Year year = yearRepository.findById(yearId)
                .orElseThrow(() -> new RuntimeException("Year not found"));
        Transmission transmission = transmissionRepository.findById(transmissionId)
                .orElseThrow(() -> new RuntimeException("Transmission not found"));
        KmDriven kmDriven = kmDrivenRepository.findById(kmDrivenId)
                .orElseThrow(() -> new RuntimeException("KmDriven not found"));
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        DetailedCarEvaluation evaluation = new DetailedCarEvaluation();
        evaluation.setYear(year);
        evaluation.setFuelType(fuelType);
        evaluation.setTransmission(transmission);
        evaluation.setKmDriven(kmDriven);
        evaluation.setCar(car);

        return evaluationRepository.save(evaluation);
    }
}
