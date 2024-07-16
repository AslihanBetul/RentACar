package com.abm.manager;

import com.abm.dto.response.RentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@FeignClient(url = "http://localhost:9093/api/v1/rent", name = "rentmanager")
public interface RentManager {
    @GetMapping("/listallrentcars")
     ResponseEntity<List<RentResponseDto>> listAllRentCardFindById(@RequestParam String token);
}
