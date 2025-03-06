package com.hms.repository;

import com.hms.entity.cars.Year;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YearRepository extends JpaRepository<Year, Long> {
    Optional<Year> findByYear(String year);
}