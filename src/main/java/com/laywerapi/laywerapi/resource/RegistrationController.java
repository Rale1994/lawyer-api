package com.laywerapi.laywerapi.resource;

import com.laywerapi.laywerapi.dto.request.UserAddRequestDTO;
import com.laywerapi.laywerapi.entity.User;
import com.laywerapi.laywerapi.entity.VerificationToken;
import com.laywerapi.laywerapi.events.RegistrationCompleteEvent;
import com.laywerapi.laywerapi.repositories.VerificationTokenRepository;
import com.laywerapi.laywerapi.services.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/registration")
@Api
public class RegistrationController {
    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository verificationTokenRepository;


    @PostMapping
    public String register(@RequestBody UserAddRequestDTO userAddRequestDTO, final HttpServletRequest request) {
        User user = userService.registerUser(userAddRequestDTO);
        // publish registration event
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "Success!, Please check your email to complete you registration!";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken.getUser().isEnabled()) {
            return "This account has already been verified! Please login.";
        }
        String verificationResult = userService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")) {
            return "Email verified successfully. Now you can login to your account!";
        }
        return "Invalid verification token!";
    }


    public String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }


}
