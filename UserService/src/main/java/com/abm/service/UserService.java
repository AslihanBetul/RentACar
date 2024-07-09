package com.abm.service;

import com.abm.config.model.UserSaveModel;

import com.abm.entity.enums.User;
import com.abm.repository.UserRepository;
import com.abm.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenManager jwtTokenManager;
@RabbitListener(queues = "queueUserSave")
    public void saveUser(UserSaveModel userSaveModel){
    User user = User.builder().authId(userSaveModel.getAuthId()).email(userSaveModel.getEmail()).build();
        userRepository.save(user);

    }
    public String userId(String token){
        Long authId = jwtTokenManager.getAuthIdFromToken(token).orElseThrow();
        User user = userRepository.findByAuthId(authId);
        return user.getId();
    }
}
