package com.hms.controller;

import com.hms.entity.cars.CarImage;
import com.hms.repository.CarImageRepository;
import com.hms.service.CarService;
import com.hms.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final S3Service s3Service;

    public FileUploadController(S3Service s3Service, CarService carService, CarImageRepository carImageRepository) {
        this.s3Service = s3Service;
    }

    // http://localhost:8080/api/files/upload
    @PostMapping("/upload/{id}")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @PathVariable Long id) {
        try {
            CarImage url = s3Service.uploadFile(file,id);
            return  ResponseEntity.ok(url);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }

    @PostMapping("/{carId}/upload-multiple")
    public ResponseEntity<List<CarImage>> uploadMultipleFiles(@PathVariable Long carId,
                                                              @RequestParam("files") MultipartFile[] files
    ) throws IOException {
        List<CarImage> uploadedImages = s3Service.uploadMultipleFiles(files, carId);
        return ResponseEntity.ok(uploadedImages);
    }
}
