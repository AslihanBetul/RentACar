package com.abm.controller;

import com.abm.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/sentcampaign")
    public void sendCampaign() throws MessagingException {
        emailService.sendBulkHtmlEmail();
    }
}
