package com.hms.controller;

import com.hms.entity.Files;
import com.hms.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/fileImport")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> storeFileIntoDB (
            @RequestParam("file") MultipartFile file
            ){
        try {
            Files files = fileService.storeFile(file);
            return new ResponseEntity<>("File inserted Successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("File not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFileFromDB (
            @PathVariable Long id
    ){
        Files file = fileService.getFile(id);
        if (file != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, file.getType())
                    .body(file.getImageData());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
