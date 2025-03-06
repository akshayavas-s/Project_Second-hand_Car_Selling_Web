package com.hms.controller;

import com.hms.entity.evaluation.DetailedCarEvaluation;
import com.hms.service.DetailedCarEvaluationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evaluation")
public class DetailedCarEvaluationController {

    private final DetailedCarEvaluationService evaluationService;

    public DetailedCarEvaluationController(DetailedCarEvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @PostMapping("/save")
    public ResponseEntity<DetailedCarEvaluation> saveEvaluation(@RequestParam Long yearId,
                                                                @RequestParam Long fuelTypeId,
                                                                @RequestParam Long transmissionId,
                                                                @RequestParam Long kmDrivenId,
                                                                @RequestParam Long carId
    ) {
        DetailedCarEvaluation savedEvaluation = evaluationService
                .saveEvaluation(yearId, fuelTypeId, transmissionId, kmDrivenId, carId);
        return ResponseEntity.ok(savedEvaluation);
    }
}
