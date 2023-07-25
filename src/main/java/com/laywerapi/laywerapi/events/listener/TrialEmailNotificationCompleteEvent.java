package com.laywerapi.laywerapi.events.listener;

import com.laywerapi.laywerapi.entity.User;
import com.laywerapi.laywerapi.events.TrialEmailNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrialEmailNotificationCompleteEvent implements ApplicationListener<TrialEmailNotificationEvent> {
    private final JavaMailSender mailSender;
    private User user;

    @Override
    public void onApplicationEvent(TrialEmailNotificationEvent event) {
        user = event.getUser();
        try {
            sendTrialNotificationMail();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        log.info("Email is sent on email: {}", user.getEmail());
    }

    public void sendTrialNotificationMail() throws UnsupportedEncodingException, MessagingException {
        String subject = "Email notification";
        String senderName = "Trial Notification";
        String mailContent = "<p> Hi, " + user.getFirstName() + ", </p>" +
                "<p>You have trials!" + "" +
                " Hello you have trials for next hour" +
                "Please check your clients" +
                "<p> Thank you <br> Users Notification Portal Service";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("golubovicrados@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
