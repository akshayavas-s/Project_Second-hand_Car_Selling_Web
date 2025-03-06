package com.hms.repository;

import com.hms.entity.cars.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Page<Car> findByModel_Name(String model, Pageable pageable);

    Optional<Car> findByBrandAndModelAndFuelTypeAndTransmissionAndYearAndKmDriven(Brand brand, Model model, FuelType fuelType,
                                   Transmission type,Year year,KmDriven kmDriven);

    @Query("SELECT c FROM Car c " +
            "JOIN c.brand b " +
            "JOIN c.transmission t " +
            "JOIN c.model m " +
            "JOIN c.fuelType f " +  
            "JOIN c.kmDriven k " +
            "JOIN c.year y " +
            "WHERE b.name = :par OR t.type = :par OR m.name = :par OR f.fuelType = :par OR k.kmDriven = :par OR y.year= :par")
    <T> List<Car> searchCar(@Param("par") T par);

}