package com.abm.service;

import com.abm.dto.request.CarSaveDto;
import com.abm.entity.Car;
import com.abm.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RentService {
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

}
