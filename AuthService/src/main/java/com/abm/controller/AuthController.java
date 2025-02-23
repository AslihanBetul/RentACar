package com.abm.controller;

import com.abm.request.AccountActivationRequestDto;
import com.abm.request.AuthRegisterDto;
import com.abm.request.LoginRequestDto;
import com.abm.request.RepasswordRequestDto;
import com.abm.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import static com.abm.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {
    private final AuthService authService;

    @PostMapping(REGISTER)
    @CrossOrigin("*")

    public ResponseEntity<String>register(@RequestBody AuthRegisterDto authRegisterDto){
        return ResponseEntity.ok(authService.register(authRegisterDto));
    }
    @PutMapping(VERIFY_ACCOUNT)
    @CrossOrigin("*")
    public ResponseEntity<String> verifyAccount(@RequestBody  @Valid AccountActivationRequestDto accountActivationRequestDto){
        return ResponseEntity.ok(authService.verifyAccount(accountActivationRequestDto));
    }
    @PostMapping(LOGIN)
    @CrossOrigin("*")

    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PutMapping(UPDATE_EMAIL)
    public ResponseEntity<Void> updateEmail(@RequestParam String email, @RequestParam Long authId){
        authService.updateEmail(email, authId);
        return ResponseEntity.ok().build();
    }
    @GetMapping(FORGET_PASSWORD)
    public ResponseEntity<String>forgetPassword(@RequestParam String email){
        return ResponseEntity.ok(authService.forgetPassword(email));
    }
    @PutMapping(UPDATE_PASSWORD)
    public ResponseEntity<String>updatePassword(@RequestBody RepasswordRequestDto repasswordRequestDto){
        authService.updatePassword(repasswordRequestDto);
        return ResponseEntity.ok("success");
    }

}
