package com.hms.controller;

import com.hms.entity.cars.Brand;
import com.hms.entity.cars.Car;
import com.hms.entity.cars.Model;
import com.hms.service.CarService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
public class CarsController {

    private CarService carService;

    public CarsController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/addBrand")
    public ResponseEntity<Brand> addCarBrand(@RequestBody Brand brand){
        Brand br = carService.addBrand(brand);
        return new ResponseEntity<>(br, HttpStatus.CREATED);
    }

    @PostMapping("/addModel")
    public ResponseEntity<?> addCarModel(
            @RequestParam String brandName,
            @RequestParam String model){
        Model mod = carService.addCarModel(brandName, model);
        if (mod!=null){
            return new ResponseEntity<>(mod, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Brand not found", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/addCar")
    public ResponseEntity<?> addCar(
            @RequestBody Car car
    ){
        Car newCar = carService.saveCar(car);
        return new ResponseEntity<>(newCar, HttpStatus.OK);
    }

    @GetMapping("/searchCar")
    public <T> List<Car> searchCar(
            @RequestParam T par
    ){
        List<Car> cars = carService.searchCar(par);
        return cars;
    }

    @GetMapping("/getCar")
    public ResponseEntity<Page<Car>> getCar(@RequestParam String model,
                                            @RequestParam(defaultValue = "0", required = false) int page,
                                            @RequestParam(defaultValue = "10", required = false) int size,
                                            @RequestParam(defaultValue = "asc", required = false) String sortDir,
                                            @RequestParam(defaultValue = "id", required = false) String sortBy
    ){
        Page<Car> carById = carService.findCarByModel(model, page, size, sortDir, sortBy);
        return ResponseEntity.ok(carById);
    }
}
