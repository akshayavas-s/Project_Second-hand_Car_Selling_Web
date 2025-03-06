package com.hms.controller;

import com.hms.entity.cars.Brand;
import com.hms.service.BulkUploadService;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bulk-upload")
public class BulkUploadController {

    private BulkUploadService bulkUploadService;

    public BulkUploadController(BulkUploadService bulkUploadService) {
        this.bulkUploadService = bulkUploadService;
    }

    @PostMapping("/brandUpload")
    public String uploadBrand(
            @RequestParam("file") MultipartFile file
    )throws Exception {
        List<Brand> brands = bulkUploadService.uploadFile(file);
        return "Brand data uploaded successfully";
    }

}
