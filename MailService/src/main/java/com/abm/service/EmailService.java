package com.abm.service;


import com.abm.config.model.InfoCustomerModel;
import com.abm.config.model.MailActivationModel;
import com.abm.dto.response.UserDto;
import com.abm.manager.UserManager;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void sendCustomerInfo(InfoCustomerModel model) throws MessagingException {

        ResponseEntity<UserDto> userDto = userManager.findByUserId(model.getUserId());
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");


        String htmlContent = "<html><body>" +
                "<table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" width=\"770px\" style=\"font-family:Arial,sans-serif;color:#000000;background-color:#f8f8f8;font-size:14px;\">" +
                "    <tbody><tr>" +
                "        <td style=\"padding-top:60px;padding-right:70px;padding-bottom:60px;padding-left:70px\">" +
                "            <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"border-color:#e6e6e6;border-width:1px;border-style:solid;background-color:#fff;padding-top:25px;padding-right:0;padding-bottom:50px;padding-left:30px\">" +
                "                <tbody><tr>" +
                "                    <td style=\"padding:0 30px 30px\">" +
                "                        <p style=\"font-family:Arial,sans-serif;color:#000000;font-size:19px;margin-top:14px;margin-bottom:0\">" +
                "                            Merhaba, " + userDto.getBody().getName() + " !" +
                "                        </p>" +
                "                        <p style=\"font-family:Arial,sans-serif;color:#000000;font-size:14px;line-height:17px;margin-top:30px;margin-bottom:0\">" +
                "                           Kiralanan araç bilgileri aşağıdadır" +
                "                        </p>" +
                "                        <ul style=\"font-family:Arial,sans-serif;color:#000000;font-size:14px;line-height:17px;margin-top:10px;margin-bottom:0\">" +
                "                            <li style=\"font-family:Arial,sans-serif;color:#000000;font-size:14px;line-height:17px;margin-top:0;margin-bottom:5px\">" +
                "                                <b>Name: </b>" + model.getName() + "<br>" +
                "                                <b>Model: </b>" + model.getModel() + "<br>" +
                "                                <b>Brand: </b>" + model.getBrand() + "<br>" +
                "                                <b>Plate: </b>" + model.getPlate() + "<br>" +
                "                                <b>Rent Date: </b>" + model.getRentDate() + "<br>" +
                "                                <b>Return Date: </b>" + model.getReturnDate() + "<br>" +
                "                                <b>Total Price: ₺</b>" + model.getTotalPrice() + "<br>" +
                "                                <b>Car Image: </b><img src=\"" + model.getImageUrl() + "\" alt=\"Car Image\" style=\"width: 300px;\"/>" +
                "                            </li>" +
                "                        </ul>" +
                "                    </td>" +
                "                </tr>" +
                "            </tbody></table>" +
                "            <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\">" +
                "                <tbody><tr>" +
                "                    <td style=\"padding-top:12px;\"></td>" +
                "                </tr>" +
                "                <tr>" +
                "                    <td style=\"font-family:Arial,sans-serif;font-size:12px;color:#888888;padding-right:30px;padding-left:30px\">" +
                "                        Lütfen bu e-postayı yanıtlamayın. Beeme Destek Ekibi'ne ulaşmak için <a href=\"https://beemeapp.com/iletisim.html\" target=\"_blank\">iletişim formunu</a> kullanın." +
                "                    </td>" +
                "                </tr>" +
                "            </tbody></table>" +
                "        </td>" +
                "    </tr></tbody></table>" +
                "</body></html>";


        helper.setText(htmlContent, true);
        helper.setTo(userDto.getBody().getEmail());
        helper.setSubject("Kiralanan Araç Bilgileri");


        mailSender.send(mimeMessage);
    }

    public void sendBulkHtmlEmail() throws MessagingException {

        String subject = "Büyük İndirim Kampanyası!";
        String text = "<html><body>" +
                "<h1>Merhaba!</h1>" +
                "<p>Büyük indirim kampanyamıza hoş geldiniz. Şimdi %50'ye varan indirimlerden yararlanabilirsiniz!</p>" +
                "<img style=\"width: 500px;\" src =\"https://www.altexsoft.com/static/blog-post-featured/2023/10/4fae8217-b4ac-49b4-b02a-91c243524301.jpg\" alt=\"Discount Image\" />" +
                "</body></html>";

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(text, true);
        helper.setTo( userManager.listAllMail().toArray(new String[0]));
        helper.setSubject(subject);
        mailSender.send(mimeMessage);
    }









}
