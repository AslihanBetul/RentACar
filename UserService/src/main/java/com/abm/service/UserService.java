package com.abm.service;

import com.abm.config.model.UserSaveModel;

import com.abm.dto.response.RentResponseDto;
import com.abm.dto.response.UpdateUserDto;
import com.abm.dto.response.UserDto;
import com.abm.entity.enums.User;
import com.abm.manager.RentManager;
import com.abm.repository.UserRepository;
import com.abm.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenManager jwtTokenManager;
    private final RentManager rentManager;
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

    public List<RentResponseDto> listAllMyRent(String token) {

        ResponseEntity<List<RentResponseDto>> listResponseEntity = rentManager.listAllRentCardFindById(token);

        List<RentResponseDto> list = new ArrayList<>();
        listResponseEntity.getBody().forEach(list::add);
        return list;

    }

    public UserDto findByUserId(String userId) {

        User user = userRepository.findById(userId).orElseThrow();
        UserDto build = UserDto.builder().name(user.getName()).Lastname(user.getLastname()).email(user.getEmail()).build();
        return build;
    }

    public void updateUser(String token, UpdateUserDto userDto) {
    Long authId = jwtTokenManager.getAuthIdFromToken(token).orElseThrow();
    User user = userRepository.findByAuthId(authId);
    user.setName(userDto.getName());
    user.setLastname(userDto.getLastname());
    userRepository.save(user);
    }
}
