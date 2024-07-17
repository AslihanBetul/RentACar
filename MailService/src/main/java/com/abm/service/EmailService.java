package com.abm.service;


import com.abm.config.model.InfoCustomerModel;
import com.abm.config.model.MailActivationModel;
import com.abm.dto.response.UserDto;
import com.abm.manager.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {


    private  final  JavaMailSender mailSender;
    private final UserManager userManager;

    @RabbitListener(queues = "queueActivation")
    public void sendEmail(MailActivationModel mailActivationModel){
        SimpleMailMessage message=new SimpleMailMessage();
       message.setTo(mailActivationModel.getEmail());

        message.setSubject("your activation code");
        message.setText("activation code"+mailActivationModel.getActivationCode());
        mailSender.send(message);
    }

    @RabbitListener(queues = "queueActivationUpdate")
    public void sendUpdateEmail(MailActivationModel mailActivationModel){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(mailActivationModel.getEmail());
        message.setSubject("your activation code");
        message.setText("activation code"+mailActivationModel.getActivationCode());
        mailSender.send(message);
    }
    @RabbitListener(queues = "queueCustomerInfo")
    public void sendCustomerInfo(InfoCustomerModel model){

        ResponseEntity<UserDto> userDto = userManager.findByUserId(model.getUserId());

        SimpleMailMessage message=new SimpleMailMessage();
        if(userDto.getBody() != null) {
            message.setTo(userDto.getBody().getEmail());
            message.setSubject("Dear "+userDto.getBody().getName()+"  "+userDto.getBody().getLastname());
        }


        message.setText("Your hire details:\n" +
                "Name: " + model.getName() + "\n" +
                "Model: " + model.getModel() + "\n" +
                "Brand: " + model.getBrand() + "\n" +
                "Plate: " + model.getPlate() + "\n" +
                "Rent Date: " + model.getRentDate() + "\n" +
                "Return Date: " + model.getReturnDate() + "\n" +
                "Total Price: ₺" + model.getTotalPrice()+ "\n" +
                "Car image " + model.getImageUrl());
        mailSender.send(message);
    }







}
