package com.abm.controller;

import com.abm.dto.response.RentResponseDto;
import com.abm.dto.response.UpdateUserDto;
import com.abm.dto.response.UserDto;
import com.abm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.abm.constant.EndPoints.USER;

@RequiredArgsConstructor
@RestController
@RequestMapping(USER)
public class UserController {
    private final UserService userService;
    @PostMapping("/userId")
    public String createUserId(@RequestParam String token) {
        String userId = userService.userId(token);
        return userId;
    }

    @GetMapping("/listAllMyRent")
    public ResponseEntity<List<RentResponseDto>> listAllMyRent(@RequestParam String token) {
        return ResponseEntity.ok(userService.listAllMyRent(token));

    }

    @GetMapping("/findbyuserId")
    public ResponseEntity<UserDto> findByUserId(@RequestParam String userId) {
        return ResponseEntity.ok(userService.findByUserId(userId));
    }

    @PostMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestParam String token, @RequestBody UpdateUserDto userDto){
        userService.updateUser(token, userDto);
        return ResponseEntity.ok("User updated successfully");
    }

    @GetMapping("/listallMail")
    public List<String> listAllMail(){
        return userService.listallMail();
    }



}
