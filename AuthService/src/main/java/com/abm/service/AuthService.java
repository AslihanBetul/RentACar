package com.abm.service;

import com.abm.config.model.MailActivationModel;
import com.abm.config.model.UserSaveModel;

import com.abm.entity.Auth;
import com.abm.entity.enums.Status;
import com.abm.exception.AuthServiceException;
import com.abm.mapper.AuthMapper;
import com.abm.repository.AuthRepository;
import com.abm.request.AccountActivationRequestDto;
import com.abm.request.AuthRegisterDto;
import com.abm.request.LoginRequestDto;
import com.abm.request.RepasswordRequestDto;
import com.abm.utility.CodeGenerator;
import com.abm.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.abm.exception.ErrorType.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final CodeGenerator codeGenerator;
    private final JwtTokenManager jwtTokenManager;
    private final RabbitTemplate rabbitTemplate;

    public String register(AuthRegisterDto authRegisterDto) {
        confirmPassword(authRegisterDto.getPassword(), authRegisterDto.getConfirmPassword());
        checkUsernameExist(authRegisterDto.getUsername());
        Auth auth = AuthMapper.INSTANCE.authRegisterDtoToAuth(authRegisterDto);
        auth.setActivationCode(codeGenerator.codeGenerator());
        Auth saved = authRepository.save(auth);
        if (saved.getId()==null){
            throw new AuthServiceException(ACCOUNT_CREATION_FAILED);

        }
        UserSaveModel model = UserSaveModel.builder().authId(saved.getId()).email(saved.getEmail()).build();
        MailActivationModel mailmodel = MailActivationModel.builder().email(saved.getEmail()).activationCode(saved.getActivationCode()).build();
        rabbitTemplate.convertAndSend("directExchange","keyUserSave",model);
        rabbitTemplate.convertAndSend("directExchange","keyActivation",mailmodel);

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
        auth.setAuthStatus(Status.ACTIVE);
        authRepository.save(auth);
        return "Account activated successfully";
    }
    private void isActivationCodeCorrect(Auth auth , AccountActivationRequestDto accountActivationRequestDto){
        if(!auth.getActivationCode().equals(accountActivationRequestDto.getActivationCode())){
            throw new AuthServiceException(ACTIVATION_CODE_WRONG);
        }


    }
    private void isAccountActivatable(Auth auth) {

        if(auth.getAuthStatus().equals(Status.ACTIVE)) {
            throw new AuthServiceException(ACCOUNT_ALREADY_ACTIVATED);
        }
        if((auth.getAuthStatus().equals(Status.BANNED))){
            throw new AuthServiceException(ACCOUNT_IS_BANNED);
        }
        if((auth.getAuthStatus().equals(Status.DELETED))){
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
        if (!auth.getAuthStatus().equals(Status.ACTIVE)){
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
        Auth saved = authRepository.save(auth);
        MailActivationModel mailmodel = MailActivationModel.builder().email(saved.getEmail()).activationCode(saved.getActivationCode()).build();
        rabbitTemplate.convertAndSend("directExchange","keyActivationUpdate",mailmodel);
        return "Check your email for new password";
    }

    public void updatePassword(RepasswordRequestDto repasswordRequestDto) {
        Auth auth = authRepository.findOptionalByEmailAndActivationCode(repasswordRequestDto.getEmail(), repasswordRequestDto.getActivationCode()).orElseThrow(() -> new AuthServiceException(REPASSWORDCODE_OR_EMAIL_WRONG));
        if (!repasswordRequestDto.getPassword().equals(repasswordRequestDto.getConfirmPassword())) {
            throw new AuthServiceException(PASSWORD_MISMATCH);

        }
        auth.setPassword(repasswordRequestDto.getPassword());
        auth.setConfirmPassword(repasswordRequestDto.getConfirmPassword());
        authRepository.save(auth);
    }
}
