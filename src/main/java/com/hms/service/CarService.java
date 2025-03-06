package com.hms.service;

import com.hms.entity.cars.*;
import com.hms.exception.ResourceExistsException;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private CarRepository carRepository;
    private BrandRepository brandRepository;
    private ModelRepository modelRepository;
    private FuelTypeRepository fuelTypeRepository;
    private TransmissionRepository transmissionRepository;
    private YearRepository yearRepository;
    private KmDrivenRepository kmDrivenRepository;

    public CarService(CarRepository carRepository, BrandRepository brandRepository, ModelRepository modelRepository, FuelTypeRepository fuelTypeRepository, TransmissionRepository transmissionRepository, YearRepository yearRepository, KmDrivenRepository kmDrivenRepository) {
        this.carRepository = carRepository;
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
        this.fuelTypeRepository = fuelTypeRepository;
        this.transmissionRepository = transmissionRepository;
        this.yearRepository = yearRepository;
        this.kmDrivenRepository = kmDrivenRepository;
    }


    public Brand addBrand(Brand brand) {
        Optional<Brand> opBrand = brandRepository.findByName(brand.getName());
        if (opBrand.isPresent()){
            throw new ResourceExistsException("Brand already exists!");
        }
        Brand save = brandRepository.save(brand);
        return save;
    }

    public Model addCarModel(String brandName, String model) {
            Optional<Brand> opBrand = brandRepository.findByName(brandName);
            if (opBrand.isEmpty()){
                throw new ResourceExistsException("Brand not found!");
            }
        Optional<Model> opModel = modelRepository.findByName(model);
            if(opModel.isEmpty()){
                Model mod = new Model();
                mod.setName(model);
                Model save = modelRepository.save(mod);
                return save;
            }
            throw new ResourceExistsException("Model already exists!");
    }

    public Car saveCar(Car car) {
        Brand brand = brandRepository.findByName(car.getBrand().getName())
                .orElseThrow(()->new ResourceNotFoundException("Brand not found!"));
        Model model = modelRepository.findByName(car.getModel().getName())
                .orElseThrow(()->new ResourceNotFoundException(("Model not found!")));
        FuelType fuelType = fuelTypeRepository.findByFuelType(car.getFuelType().getFuelType())
                .orElseThrow(()->new ResourceNotFoundException("Fuel type not found!"));
        Transmission type = transmissionRepository.findByType(car.getTransmission().getType())
                .orElseThrow(()-> new ResourceNotFoundException("Transmission Type not found"));
        KmDriven kmDriven = kmDrivenRepository.findByKmDriven(car.getKmDriven().getKmDriven())
                .orElseThrow(()->new ResourceNotFoundException("Km Driven Not Found"));
        Year year = yearRepository.findByYear(car.getYear().getYear())
                .orElseThrow(()->new ResourceNotFoundException("Year Not Found"));

        car.setBrand(brand);;
        car.setModel(model);
        car.setFuelType(fuelType);
        car.setTransmission(type);
        car.setKmDriven(kmDriven);
        car.setYear(year);

        Optional<Car> existingCar = carRepository.findByBrandAndModelAndFuelTypeAndTransmissionAndYearAndKmDriven(
                brand, model, fuelType, type, year, kmDriven);

        if(existingCar.isPresent()){
            throw new ResourceExistsException("Car already exists");
        }

        Car newCar = carRepository.save(car);
        return newCar;
    }

    public <T> List<Car> searchCar(T par) {
        T formattedInput = yearCategory(par);
            return carRepository.searchCar(formattedInput);
        }

    private <T> T yearCategory(T par) {
        if(par instanceof String){
            try {
                int year =Integer.parseInt((String) par);
                return (T) getYearCategory(year);
            }catch(NumberFormatException e){
                return par;
            }
        }
        return par;
    }

    private String getYearCategory(int year) {
        if (year <= 2008) return "2008";
        if (year <= 2012) return "2012";
        if (year <= 2016) return "2016";
        if (year <= 2020) return "2020";
        if (year <= 2024) return "2024";
        return "2024";
    }

    public Page<Car> findCarByModel(String model,
                           int page,
                           int size,
                           String sortDir,
                           String sortBy
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return carRepository.findByModel_Name(model, pageable);
    }

    public Car findCarById(Long id) {
        Optional<Car> byId = carRepository.findById(id);
        return byId.orElseThrow(() -> new ResourceNotFoundException("Car not found!"));
    }
}
