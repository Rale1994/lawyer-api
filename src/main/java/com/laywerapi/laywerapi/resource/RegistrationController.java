package com.laywerapi.laywerapi.resource;

import com.laywerapi.laywerapi.dto.request.PasswordResetRequestDTO;
import com.laywerapi.laywerapi.dto.request.RegisterUserRequestDTO;
import com.laywerapi.laywerapi.entity.User;
import com.laywerapi.laywerapi.entity.VerificationToken;
import com.laywerapi.laywerapi.events.RegistrationCompleteEvent;
import com.laywerapi.laywerapi.repositories.VerificationTokenRepository;
import com.laywerapi.laywerapi.services.PasswordResetService;
import com.laywerapi.laywerapi.services.UserService;
import com.laywerapi.laywerapi.services.VerificationTokenService;
import com.laywerapi.laywerapi.services.implementation.PasswordResetServiceImpl;
import com.laywerapi.laywerapi.shared.Constants;
import com.laywerapi.laywerapi.shared.Utils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.BASE_URL + "/registration")
@Api
public class RegistrationController {
    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenService verificationTokenService;
    private final PasswordResetService passwordResetService;


    @PostMapping
    public String register(@RequestBody RegisterUserRequestDTO registerUserRequestDTO, final HttpServletRequest request) {
        User user = userService.registerUser(registerUserRequestDTO);
        // publish registration event
        publisher.publishEvent(new RegistrationCompleteEvent(user, Utils.applicationUrl(request)));
        return "Success!, Please check your email to complete you registration!";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) {
        return verificationTokenService.verifyToken(token);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody PasswordResetRequestDTO passwordResetRequestDTO,
                                                 final HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        return new ResponseEntity<>(passwordResetService.forgotUserPassword(passwordResetRequestDTO, request), HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestBody PasswordResetRequestDTO passwordResetRequestDTO) {
        return passwordResetService.resetPassword(token, passwordResetRequestDTO);
    }
}
