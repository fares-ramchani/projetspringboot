package com.example.cyberParc.coucheService;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class SendMailMessage {
    private final JavaMailSender javaMailSender;
    public void sendMailToClient(String toEmail, String body, String subject) throws jakarta.mail.MessagingException {
        jakarta.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("faresramchani@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);
        javaMailSender.send(mimeMessage);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void triggerEmail() {

        System.out.println("Application Ready Event Triggered");
    }
}
