package com.abm.controller;


import com.abm.dto.response.CarResponseDto;
import com.abm.dto.response.RentResponseDto;
import com.abm.entity.CarStatus;
import com.abm.request.CarSaveDto;
import com.abm.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.abm.constant.EndPoints.*;

@RequiredArgsConstructor
@RequestMapping(CAR)
@RestController
public class CarController {
    private final CarService carService;

    @PostMapping(SAVE)
    public ResponseEntity<String> save(@RequestBody CarSaveDto dto){
        return ResponseEntity.ok(carService.save(dto));
    }
    @PutMapping(UPDATE)
    public ResponseEntity<String> updatePrice(@RequestParam String id, Double price){
        carService.update(price,id);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/listallcar")
    public ResponseEntity<List<CarResponseDto>> listAllCar() {
        return ResponseEntity.ok(carService.listAllCar());

    }

    @GetMapping("/listallavailablecar")
    public ResponseEntity<List<CarResponseDto>> listAllAvailableCar(CarStatus status) {
        return ResponseEntity.ok(carService.listAllAvailableCar(status));

    }

    @GetMapping("/listAllCarName")
    public ResponseEntity<List<CarResponseDto>> listAllCarName(String name) {
        return ResponseEntity.ok(carService.listAllCarName(name));

    }




}
