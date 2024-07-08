package com.abm.controller;

import com.abm.dto.request.CarSaveDto;
import com.abm.dto.request.RentSaveDto;
import com.abm.service.CarService;
import com.abm.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.abm.constant.EndPoints.*;

@RequiredArgsConstructor
@RequestMapping(RENT)
@RestController
public class RentController {
    private final RentService rentService;

    @PostMapping(SAVE)
    public ResponseEntity<String> save(@RequestBody RentSaveDto dto){
        return ResponseEntity.ok(rentService.save(dto));
    }
}
