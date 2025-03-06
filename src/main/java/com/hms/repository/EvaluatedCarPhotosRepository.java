package com.hms.repository;

import com.hms.entity.evaluation.EvaluatedCarPhotos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluatedCarPhotosRepository extends JpaRepository<EvaluatedCarPhotos, Long> {
}