package com.hms.repository;

import com.hms.entity.cars.CarImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarImageRepository extends JpaRepository<CarImage, Long> {
}