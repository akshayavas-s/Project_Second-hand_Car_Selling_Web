package com.hms.repository;

import com.hms.entity.cars.Transmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransmissionRepository extends JpaRepository<Transmission, Long> {
    Optional<Transmission> findByType(String type);
}