package com.abm.service;

import com.abm.config.model.UserSaveModel;
import com.abm.entity.enums.User;
import com.abm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
@RabbitListener(queues = "queueUserSave")
    public void saveUser(UserSaveModel userSaveModel){
    User user = User.builder().authId(userSaveModel.getAuthId()).email(userSaveModel.getEmail()).build();
        userRepository.save(user);

    }
}
