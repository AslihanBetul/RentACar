package com.abm.manager;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://localhost:9091/api/v1/user", name = "usermanager")
public interface UserManager {
    @PostMapping("/userId")
    String createUserId(@RequestParam String token);

}
