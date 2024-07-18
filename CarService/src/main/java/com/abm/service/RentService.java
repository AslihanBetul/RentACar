package com.abm.service;

import com.abm.config.model.InfoCustomerModel;
import com.abm.dto.response.CarResponseDto;
import com.abm.dto.response.RentResponseDto;
import com.abm.entity.Car;
import com.abm.entity.CarStatus;
import com.abm.entity.Rent;
import com.abm.exception.CarServiceException;
import com.abm.exception.ErrorType;
import com.abm.manager.UserManager;
import com.abm.repository.RentRepository;
import com.abm.request.RentSaveDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class RentService {
    private final RentRepository rentRepository;
    private final CarService carService;
    private final UserManager userManager;
    private final RabbitTemplate rabbitTemplate;




    public String save(RentSaveDto dto,String token) {
        String userId = userManager.createUserId(token);

        date(dto);


        double carPrice = getCarPrice(dto.getCarId(),dto.getRentDate(), dto.getReturnDate());
        Rent rent =Rent.builder()
                .carId(dto.getCarId())
                .userId(userId)
                .rentDate(dto.getRentDate())
                .returnDate(dto.getReturnDate())
                .totalPrice(carPrice)
                .build();

        Car car = carService.findById(dto.getCarId());
        if (car.getCarStatus() == CarStatus.RENTED)  {
            throw new CarServiceException(ErrorType.CAR_ALREADY_RENTED);
        }
        car.setCarStatus(CarStatus.RENTED);

        carService.save(car);
        rentRepository.save(rent);
        InfoCustomerModel customerModel = InfoCustomerModel.builder().name(car.getName()).userId(rent.getUserId()).brand(car.getBrand()).model(car.getModel()).plate(car.getPlate()).rentDate(rent.getRentDate()).returnDate(rent.getReturnDate()).totalPrice(rent.getTotalPrice()).imageUrl(car.getImageUrl()).build();
        rabbitTemplate.convertAndSend("directExchange","keyCustomerInfo", customerModel);
        return "Car rentted successfully car price="+carPrice;
    }


    private static void date(RentSaveDto dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate  firstDate = LocalDate.parse(dto.getRentDate(), formatter);
        LocalDate  secondDate = LocalDate.parse(dto.getReturnDate(), formatter);
        LocalDate now = LocalDate.now();

        if (now.isAfter(firstDate)) {
            throw new CarServiceException(ErrorType.INVALID_DATE);
        } else if (firstDate.isAfter(secondDate)) {
            throw new CarServiceException(ErrorType.INVALID_DATE);
        }
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

    public List<RentResponseDto> listAllRentCarFindById(String token) {

        String userId = userManager.createUserId(token);
        List<Rent> rents = rentRepository. getRentByUserId(userId);
        List<RentResponseDto> rentResponseDtos = new ArrayList<>();
        rents.forEach(rent -> {
            RentResponseDto rentResponseDto = RentResponseDto.builder()
                   .carId(rent.getCarId())
                    .userId(rent.getUserId())
                   .rentId(rent.getId())
                   .rentDate(rent.getRentDate())
                   .returnDate(rent.getReturnDate())
                   .totalPrice(rent.getTotalPrice())
                   .build();
            rentResponseDtos.add(rentResponseDto);


        });

        return rentResponseDtos;
    }

    public void rentCheck(){
        List<Rent> rent = rentRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        rent.forEach(rent1 -> {
            LocalDate  date = LocalDate.parse(rent1.getReturnDate(), formatter);
            LocalDate now = LocalDate.now();
            if(now.isAfter(date)){
                Car car = carService.findById(rent1.getCarId());
                car.setCarStatus(CarStatus.AVAILABLE);
                carService.save(car);
                rentRepository.delete(rent1);
            }
        });



    }

    @PostConstruct

    public void timer() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable minuteTask = () -> {
            rentCheck();
        };
        long initialDelay = 0; // Başlangıçta hemen çalıştır
        long period = 30;
        scheduler.scheduleAtFixedRate(minuteTask, initialDelay, period, TimeUnit.SECONDS);
    }


    }



