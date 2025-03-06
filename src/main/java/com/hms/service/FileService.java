package com.hms.service;

import com.hms.entity.Files;
import com.hms.repository.FilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class FileService {
    @Autowired
    private FilesRepository filesRepository;

    public Files storeFile(MultipartFile file) throws IOException {
        Files files = new Files();
        files.setName(file.getOriginalFilename());
        files.setType(file.getContentType());
        files.setImageData(file.getBytes());

        return filesRepository.save(files);
    }

    public Files getFile(Long id) {
        Optional<Files> byId = filesRepository.findById(id);
        if(byId.isPresent()) {
            Files files = byId.get();
            return files;
        }
        return null;
    }
}
