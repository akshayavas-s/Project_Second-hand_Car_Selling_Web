package com.hms.service;

import com.hms.config.S3Config;
import com.hms.entity.cars.Car;
import com.hms.entity.cars.CarImage;
import com.hms.entity.evaluation.DetailedCarEvaluation;
import com.hms.entity.evaluation.EvaluatedCarPhotos;
import com.hms.repository.CarImageRepository;
import com.hms.repository.DetailedCarEvaluationRepository;
import com.hms.repository.EvaluatedCarPhotosRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class S3Service {

    private S3Client s3Client;
    
    private CarService carService;

    private CarImageRepository carImageRepository;

    private final EvaluatedCarPhotosRepository evaluatedCarPhotosRepository;
    private final DetailedCarEvaluationRepository detailedCarEvaluationRepository;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(S3Client s3Client, CarService carService, CarImageRepository carImageRepository, EvaluatedCarPhotosRepository evaluatedCarPhotosRepository, DetailedCarEvaluationRepository detailedCarEvaluationRepository) {
        this.s3Client = s3Client;
        this.carService = carService;
        this.carImageRepository = carImageRepository;
        this.evaluatedCarPhotosRepository = evaluatedCarPhotosRepository;
        this.detailedCarEvaluationRepository = detailedCarEvaluationRepository;
    }


    public CarImage uploadFile(MultipartFile file, Long id) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

        S3Utilities s3Utilities = s3Client.utilities();
        URL fileUrl = s3Utilities.getUrl(b -> b.bucket(bucketName).key(fileName));

        Car car = carService.findCarById(id);
        CarImage image = new CarImage();
        image.setUrl(fileUrl.toString());
        image.setCar(car);
        CarImage savedUrl = carImageRepository.save(image);

        return savedUrl;
    }

    public List<CarImage> uploadMultipleFiles(MultipartFile[] files, Long carId) throws IOException {
        List<CarImage> savedImages = new ArrayList<>();
        Car car = carService.findCarById(carId);

        for (MultipartFile file : files) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            S3Utilities s3Utilities = s3Client.utilities();
            URL fileUrl = s3Utilities.getUrl(b -> b.bucket(bucketName).key(fileName));

            CarImage image = new CarImage();
            image.setUrl(fileUrl.toString());
            image.setCar(car);

            savedImages.add(carImageRepository.save(image));
        }
        return savedImages;
    }

    public List<EvaluatedCarPhotos> uploadActualMultipleFiles(MultipartFile[] files, Long carId) throws IOException {
        List<EvaluatedCarPhotos> savedImages = new ArrayList<>();
        DetailedCarEvaluation evaluation = detailedCarEvaluationRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Detailed Car Evaluation not found for ID: " + carId));

        for (MultipartFile file : files) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            S3Utilities s3Utilities = s3Client.utilities();
            URL fileUrl = s3Utilities.getUrl(b -> b.bucket(bucketName).key(fileName));

            EvaluatedCarPhotos image = new EvaluatedCarPhotos();
            image.setPhotoUrl(fileUrl.toString());
            image.setDetailedCarEvaluation(evaluation);

            savedImages.add(evaluatedCarPhotosRepository.save(image));
        }
        return savedImages;
    }
}
