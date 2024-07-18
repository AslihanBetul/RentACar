package com.abm.service;


import com.abm.dto.response.CarResponseDto;
import com.abm.entity.Car;
import com.abm.entity.CarStatus;
import com.abm.repository.CarRepository;
import com.abm.request.CarSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CarService {
    private final CarRepository carRepository;


    public String save(CarSaveDto dto) {
        Car car = Car.builder()
                .name(dto.getName())
                .brand(dto.getBrand())
                .model(dto.getModel())
                .plate(dto.getPlate())
                .color(dto.getColor())
                .fuel(dto.getFuel())
                .pricePerDay(dto.getPricePerDay())
                .sasiNo(dto.getSasiNo())
                .imageUrl(dto.getImageUrl())
                .build();
        carRepository.save(car);
        return "Car saved";
    }
    public String save(Car car){
        carRepository.save(car);
        return "Car saved";
    }

    public void update(Double price,String id) {
        Car car = carRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Car not found"));

        car.setPricePerDay(price);

        carRepository.save(car);
    }

    public Car findById(String carId) {
        return carRepository.findById(carId).orElse(null);

    }

    public List<CarResponseDto> listAllCar() {
        List<Car> all = carRepository.findAll();
        List<CarResponseDto> carList = new ArrayList<>();

        all.forEach(car -> {
           CarResponseDto carResponseDto =  CarResponseDto.builder()
                   .id(car.getId())
                   .name(car.getName())
                   .brand(car.getBrand())
                   .model(car.getModel())
                   .plate(car.getPlate())
                   .color(car.getColor())
                   .fuel(car.getFuel())
                   .pricePerDay(car.getPricePerDay())
                   .sasiNo(car.getSasiNo())
                   .imageUrl(car.getImageUrl())
                   .carStatus(car.getCarStatus())
                   .build();
            carList.add(carResponseDto);
        });

        return  carList;

    }

    public List<CarResponseDto> listAllAvailableCar(CarStatus status) {
        return listAllCar().stream().filter(car -> car.getCarStatus() == status).toList();

        // List<CarResponseDto> carList = new ArrayList<>();

//     listAllCar().forEach(car ->{
//         if(car.getCarStatus() == status){
//             carList.add(car);
//         }
//     } );


    }

    public List<CarResponseDto> listAllCarName(String name) {

        return listAllCar().stream().filter(car -> car.getName().contains(name)).toList();


    }
}
