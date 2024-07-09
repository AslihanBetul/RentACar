package com.abm.controller;


import com.abm.request.CarSaveDto;
import com.abm.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
