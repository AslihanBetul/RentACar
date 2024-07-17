package com.abm.service;

import com.abm.config.model.InfoCustomerModel;
import com.abm.dto.response.RentResponseDto;
import com.abm.entity.Car;
import com.abm.entity.CarStatus;
import com.abm.entity.Rent;
import com.abm.exception.CarServiceException;
import com.abm.exception.ErrorType;
import com.abm.manager.UserManager;
import com.abm.repository.RentRepository;
import com.abm.request.RentSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RentService {
    private final RentRepository rentRepository;
    private final CarService carService;
    private final UserManager userManager;
    private final RabbitTemplate rabbitTemplate;


    public String save(RentSaveDto dto,String token) {
        String userId = userManager.createUserId(token);

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
}
