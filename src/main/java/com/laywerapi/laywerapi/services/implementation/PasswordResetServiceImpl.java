package com.laywerapi.laywerapi.services.implementation;

import com.laywerapi.laywerapi.dto.request.PasswordResetRequestDTO;
import com.laywerapi.laywerapi.entity.PasswordResetToken;
import com.laywerapi.laywerapi.entity.User;
import com.laywerapi.laywerapi.repositories.PasswordResetTokenRepository;
import com.laywerapi.laywerapi.repositories.UserRepository;
import com.laywerapi.laywerapi.services.PasswordResetService;
import com.laywerapi.laywerapi.shared.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetServiceImpl implements PasswordResetService {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userServiceImpl;

    @Override
    public String resetPassword(String token, PasswordResetRequestDTO passwordResetRequestDTO) {
        log.info("Getting token for reset password...");
        String tokenValidationResult = validatePasswordResetToken(token);
        if (tokenValidationResult.equalsIgnoreCase("valid")) {
            log.info("Token is valid...");
            Optional<User> userOptional = findUserByPasswordToken(token);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                userServiceImpl.resetUserPassword(user, passwordResetRequestDTO.getNewPassword());
                log.info("Password has been reset...");
                return "Password has been reset successfully";
            }
        }
        log.warn("Invalid token!!!");
        return "Invalid password reset token";
    }

    @Override
    public String forgotUserPassword(PasswordResetRequestDTO passwordResetRequestDTO, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        log.info("Sending request for forgot password...");
        Optional<User> optionalUser = userRepository.findByEmail(passwordResetRequestDTO.getEmail());
        String passwordResetUrl = "";
        if (optionalUser.isPresent()) {
            String passwordResetToken = Utils.generateResetToken();
            User user = optionalUser.get();
            createPasswordResetToken(user, passwordResetToken);
            log.info("Token is created and prepared to sent...");
            passwordResetUrl = Utils.passwordForgotEmailLink(passwordResetToken);
            sendPasswordResetEmail(user, passwordResetUrl);
            log.info("Email with link and token is send!");
        }
        return passwordResetUrl;
    }

    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null) {
            log.warn("Token is not valid!");
            return "Token is not valid, generated another one!";
        }
        if (isTokenExpired(passwordResetToken)) {
            return "Token already expired";
        }
        return "valid";
    }

    public Optional<User> findUserByPasswordToken(String passwordToken) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(passwordToken).getUser());
    }


    private void sendPasswordResetEmail(User user, String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Password reset";
        String senderName = "User reset password LawyerAPI";
        String mailContent = "<p> Hi, " + user.getFirstName() + ", </p>" +
                "<p>You have request to reset your password." + "" +
                " Please click on the link below to proceed with the password reset.</p>" +
                "<a href=\"" + url + "\">Reset password link</a>" +
                "<p> Best regards <br> Your Lawyer Api Team";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("email@example.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

    private void createPasswordResetToken(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    public boolean isTokenExpired(PasswordResetToken token) {
        return token.getExpiryDate().isBefore(LocalDateTime.now());
    }

}
