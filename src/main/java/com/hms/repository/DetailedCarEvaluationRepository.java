package com.hms.repository;

import com.hms.entity.evaluation.DetailedCarEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailedCarEvaluationRepository extends JpaRepository<DetailedCarEvaluation, Long> {
}