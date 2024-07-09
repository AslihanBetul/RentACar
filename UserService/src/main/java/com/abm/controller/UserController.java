package com.abm.controller;

import com.abm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
