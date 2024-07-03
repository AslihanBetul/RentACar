package com.abm.sevice;


import com.abm.config.MailActivationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {


    private  final  JavaMailSender mailSender;

    @RabbitListener(queues = "queueActivation")
    public void sendEmail(MailActivationModel mailActivationModel){
        SimpleMailMessage message=new SimpleMailMessage();
       message.setTo(mailActivationModel.getEmail());

        message.setSubject("your activation code");
        message.setText("activation code"+mailActivationModel.getActivationCode());
        mailSender.send(message);
    }




}
