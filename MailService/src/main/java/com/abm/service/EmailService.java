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
                "<table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" width=\"770px\" style=\"font-family:Arial,sans-serif;color:#000000;background-color:#f8f8f8;font-size:14px;\">\n" +
                "    <tbody><tr>\n" +
                "        <td style=\"padding-top:60px;padding-right:70px;padding-bottom:60px;padding-left:70px\">\n" +
                "            <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"border-color:#e6e6e6;border-width:1px;border-style:solid;background-color:#fff;padding-top:25px;padding-right:0;padding-bottom:50px;padding-left:30px\">\n" +
                "                <tbody><tr>\n" +
                "                    <td style=\"padding:0 30px 30px\">\n" +
                "                        <p style=\"font-family:Arial,sans-serif;color:#000000;font-size:19px;margin-top:14px;margin-bottom:0\">\n" +
                "                            Merhaba, " + userDto.getBody().getName() + " !\n" +
                "                        </p>\n" +
                "                        <p style=\"font-family:Arial,sans-serif;color:#000000;font-size:14px;line-height:17px;margin-top:30px;margin-bottom:0\">\n" +
                "                           Kiralanan araç bilgileri aşağıdadır\n" +
                "                        </p>\n" +
                "                        <ul style=\"font-family:Arial,sans-serif;color:#000000;font-size:14px;line-height:17px;margin-top:10px;margin-bottom:0\">\n" +
                "                            <li style=\"font-family:Arial,sans-serif;color:#000000;font-size:14px;line-height:17px;margin-top:0;margin-bottom:5px\">\n" +
                "                                <b> " +
                "                            </li>\n" +
                "                        </ul>\n" +
                "                        <ul style=\"font-family:Arial,sans-serif;color:#000000;font-size:14px;line-height:17px;margin-top:10px;margin-bottom:0\">\n" +
                "                            <li style=\"font-family:Arial,sans-serif;color:#000000;font-size:14px;line-height:17px;margin-top:0;margin-bottom:5px\">\n" +
                "                                <b>Name: </b>" + model.getName() + "<br>\n" +
                "                                <b>Model: </b>" + model.getModel() + "<br>\n" +
                "                                <b>Brand: </b>" + model.getBrand() + "<br>\n" +
                "                                <b>Plate: </b>" + model.getPlate() + "<br>\n" +
                "                                <b>Rent Date: </b>" + model.getRentDate() + "<br>\n" +
                "                                <b>Return Date: </b>" + model.getReturnDate() + "<br>\n" +
                "                                <b>Total Price: ₺</b>" + model.getTotalPrice() + "<br>\n" +
                "                                <b>Car Image: </b><img src=\"" + model.getImageUrl() + "\" alt=\"Car Image\">\n" +
                "                            </li>\n" +
                "                        </ul>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody></table>\n" +
                "            <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\">\n" +
                "                <tbody><tr>\n" +
                "                    <td style=\"padding-top:12px;\"></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td style=\"font-family:Arial,sans-serif;font-size:12px;color:#888888;padding-right:30px;padding-left:30px\">\n" +
                "                        Lütfen bu e-postayı yanıtlamayın. Beeme Destek Ekibi'ne ulaşmak için <a href=\"https://beemeapp.com/iletisim.html\" target=\"_blank\">iletişim formunu</a> kullanın.\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody></table>\n" +
                "        </td>\n" +
                "    </tr></tbody></table> </body></html>";
        helper.setText(htmlContent, true);
        helper.setTo(userDto.getBody().getEmail());
        helper.setSubject("your activation code");

        mailSender.send(mimeMessage);



    }









}
