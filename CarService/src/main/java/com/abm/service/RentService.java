package com.abm.service;

import com.abm.dto.request.CarSaveDto;
import com.abm.dto.request.RentSaveDto;
import com.abm.entity.Car;
import com.abm.entity.CarStatus;
import com.abm.entity.Rent;
import com.abm.repository.CarRepository;
import com.abm.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Service
public class RentService {
    private final RentRepository rentRepository;
    private final CarService carService;


    public String save(RentSaveDto dto) {
        double carPrice = getCarPrice(dto.getCarId(),dto.getRentDate(), dto.getReturnDate());
        Rent rent =Rent.builder()
                .carId(dto.getCarId())
                .userId(dto.getUserId())
                .rentDate(dto.getRentDate())
                .returnDate(dto.getReturnDate())
                .totalPrice(carPrice)
                .build();

        Car car = carService.findById(dto.getCarId());
        car.setCarStatus(CarStatus.RENTED);

      carService.save(car);
        return "Car rentted successfully car price="+carPrice;
    }

    public double getCarPrice(String CarId,String rentDate,String returnDate) {
        Car car = carService.findById(CarId);
        double price=car.getPricePerDay();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate  firstDate = LocalDate.parse(rentDate, formatter);
        LocalDate  secondDate = LocalDate.parse(returnDate, formatter);

        long daysBetween = ChronoUnit.DAYS.between(firstDate, secondDate);


        return price*daysBetween;
    }

}
