package com.abm.manager;

import com.abm.dto.response.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(url = "http://localhost:9091/api/v1/user", name = "usermanager")
public interface UserManager {

    @GetMapping("/findbyuserId")
     ResponseEntity<UserDto> findByUserId( @RequestParam String userId);

    @GetMapping("/listallMail")
  List<String>listAllMail();
}
