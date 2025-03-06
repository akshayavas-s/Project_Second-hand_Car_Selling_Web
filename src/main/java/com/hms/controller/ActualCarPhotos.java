package com.hms.controller;

import com.hms.entity.evaluation.EvaluatedCarPhotos;
import com.hms.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ap1/v1/actual-car-photos")
public class ActualCarPhotos {

    private S3Service s3Service;

    public ActualCarPhotos(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload/{carId}")
    public ResponseEntity<?> uploadActualCarPhotos(
            @RequestPart("file") MultipartFile[] file,
            @PathVariable long carId
            ) {
        try {
            List<EvaluatedCarPhotos> url = s3Service.uploadActualMultipleFiles(file,carId);
            return  ResponseEntity.ok(url);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }
}
