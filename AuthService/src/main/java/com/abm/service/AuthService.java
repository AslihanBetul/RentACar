package com.abm.service;

import com.abm.dto.request.AccountActivationRequestDto;
import com.abm.dto.request.AuthRegisterDto;
import com.abm.dto.request.LoginRequestDto;
import com.abm.dto.request.RepasswordRequestDto;
import com.abm.entity.Auth;
import com.abm.entity.enums.AuthStatus;
import com.abm.exception.AuthServiceException;
import  static com.abm.exception.ErrorType.*;

import com.abm.exception.ErrorType;
import com.abm.mapper.AuthMapper;
import com.abm.repository.AuthRepository;
import com.abm.utility.CodeGenerator;
import com.abm.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final CodeGenerator codeGenerator;
    private final JwtTokenManager jwtTokenManager;

    public String register(AuthRegisterDto authRegisterDto) {
confirmPassword(authRegisterDto.getPassword(), authRegisterDto.getConfirmPassword());
checkUsernameExist(authRegisterDto.getUsername());
        Auth auth = AuthMapper.INSTANCE.authRegisterDtoToAuth(authRegisterDto);
        auth.setActivationCode(codeGenerator.codeGenerator());
        Auth saved = authRepository.save(auth);
        if (saved.getId()==null){
            throw new AuthServiceException(ACCOUNT_CREATION_FAILED);


        }
  return "Account created successfully. Please check your email for activation code";
    }

    private void confirmPassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new AuthServiceException(PASSWORD_MISMATCH);
        }
    }
    private void checkUsernameExist(String username) {
        if(authRepository.existsByUsername(username)) {
            throw new AuthServiceException(USERNAME_ALREADY_TAKEN);
        }
    }


    public String verifyAccount(AccountActivationRequestDto accountActivationRequestDto) {
        Auth auth = checkAuthByUsernameAndPassword(accountActivationRequestDto.getUsername(), accountActivationRequestDto.getPassword());
       isAccountActivatable(auth);
        isActivationCodeCorrect(auth,accountActivationRequestDto);
        auth.setAuthStatus(AuthStatus.ACTIVE);
        authRepository.save(auth);
        return "Account activated successfully";
    }
    private void isActivationCodeCorrect(Auth auth ,AccountActivationRequestDto accountActivationRequestDto){
        if(!auth.getActivationCode().equals(accountActivationRequestDto.getActivationCode())){
            throw new AuthServiceException(ACTIVATION_CODE_WRONG);
        }


    }
    private void isAccountActivatable(Auth auth) {
        System.out.println("deneme");
        if(auth.getAuthStatus().equals(AuthStatus.ACTIVE)) {
            throw new AuthServiceException(ACCOUNT_ALREADY_ACTIVATED);
        }
        if((auth.getAuthStatus().equals(AuthStatus.BANNED))){
            throw new AuthServiceException(ACCOUNT_IS_BANNED);
        }
        if((auth.getAuthStatus().equals(AuthStatus.DELETED))){
            throw new AuthServiceException(ACCOUNT_IS_DELETED);

        }

    }



    private Auth checkAuthByUsernameAndPassword(String username, String password){
        return authRepository.findOptionalByUsernameAndPassword(username,password)
                .orElseThrow(() -> new AuthServiceException(USERNAME_OR_PASSWORD_WRONG));
    }

    public String login(LoginRequestDto loginRequestDto) {
        Auth auth = authRepository.findOptionalByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword())
                .orElseThrow(() -> new AuthServiceException(EMAIL_OR_PASSWORD_WRONG));
        if (!auth.getAuthStatus().equals(AuthStatus.ACTIVE)){
            throw new AuthServiceException(ACCOUNT_IS_NOT_ACTIVE);
        }
        return jwtTokenManager.createToken(auth).orElseThrow(() -> new AuthServiceException(TOKEN_CREATION_FAILED));
    }

    public void updateEmail(String email, Long authId) {
        Auth auth = authRepository.findById(authId)
                .orElseThrow(() -> new AuthServiceException(USER_NOT_FOUND));
        auth.setEmail(email);
        authRepository.save(auth);
    }

    public String forgetPassword(String email) {
        Auth auth = authRepository.findOptionalByEmail(email)
                .orElseThrow(() -> new AuthServiceException(EMAIL_NOT_FOUND));
        auth.setActivationCode(codeGenerator.codeGenerator());
        authRepository.save(auth);
        return "Check your email for new password";
    }

    public void updatePassword(RepasswordRequestDto repasswordRequestDto) {
        Auth auth = authRepository.findOptionalByEmailAndPassword(repasswordRequestDto.getEmail(), repasswordRequestDto.getRePasswordCode()).orElseThrow(() -> new AuthServiceException(REPASSWORDCODE_OR_EMAIL_WRONG));
        if (!repasswordRequestDto.getPassword().equals(repasswordRequestDto.getConfirmPassword())) {
            throw new AuthServiceException(PASSWORD_MISMATCH);

        }
        auth.setPassword(repasswordRequestDto.getPassword());
        authRepository.save(auth);
    }
}
