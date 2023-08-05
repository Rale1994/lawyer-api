package com.laywerapi.laywerapi.services;

import com.laywerapi.laywerapi.dto.request.PasswordResetRequestDTO;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public interface PasswordResetService {
//    void forgotPassword(String email, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException;

    String resetPassword(String token, PasswordResetRequestDTO passwordResetRequestDTO);

    String forgotUserPassword(PasswordResetRequestDTO passwordResetRequestDTO, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException;
}
