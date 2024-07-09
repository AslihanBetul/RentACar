package com.abm.controller;


import com.abm.request.RentSaveDto;
import com.abm.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.abm.constant.EndPoints.RENT;
import static com.abm.constant.EndPoints.SAVE;

@RequiredArgsConstructor
@RequestMapping(RENT)
@RestController
public class RentController {
    private final RentService rentService;

    @PostMapping(SAVE)
    public ResponseEntity<String> save(@RequestBody RentSaveDto dto, @RequestParam String token){
        return ResponseEntity.ok(rentService.save(dto,token));
    }
}
