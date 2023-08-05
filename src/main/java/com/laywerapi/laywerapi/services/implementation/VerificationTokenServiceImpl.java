package com.laywerapi.laywerapi.services.implementation;

import com.laywerapi.laywerapi.entity.VerificationToken;
import com.laywerapi.laywerapi.repositories.VerificationTokenRepository;
import com.laywerapi.laywerapi.services.UserService;
import com.laywerapi.laywerapi.services.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserService userService;

    @Override
    public String verifyToken(String token) {
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
}
