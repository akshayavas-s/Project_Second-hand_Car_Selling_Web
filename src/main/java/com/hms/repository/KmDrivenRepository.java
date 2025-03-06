package com.hms.repository;

import com.hms.entity.cars.KmDriven;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KmDrivenRepository extends JpaRepository<KmDriven, Long> {
    Optional<KmDriven> findByKmDriven(int kmDriven);
}