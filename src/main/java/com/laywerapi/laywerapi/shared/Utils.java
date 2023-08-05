package com.laywerapi.laywerapi.shared;

import com.laywerapi.laywerapi.dto.request.ClientRequestDTO;
import com.laywerapi.laywerapi.dto.request.UserUpdateRequestDTO;
import com.laywerapi.laywerapi.entity.Client;
import com.laywerapi.laywerapi.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class Utils {
    public static String generateResetToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public static String passwordForgotEmailLink(String passwordResetToken) {
        return "http://localhost:8082/api/v1/registration/reset-password?token=" + passwordResetToken;
    }

    public <T, U> T checkingForUpdates(T entity, U requestDTO) {
        if (entity instanceof User && requestDTO instanceof UserUpdateRequestDTO) {
            User user = (User) entity;
            UserUpdateRequestDTO userUpdateRequestDTO = (UserUpdateRequestDTO) requestDTO;

            if (StringUtils.isNoneBlank(userUpdateRequestDTO.getFirstName())) {
                user.setFirstName(userUpdateRequestDTO.getFirstName());
            }
            if (StringUtils.isNoneBlank(userUpdateRequestDTO.getLastName())) {
                user.setLastName(userUpdateRequestDTO.getLastName());
            }
            if (StringUtils.isNoneBlank(userUpdateRequestDTO.getPhone())) {
                user.setPhone(userUpdateRequestDTO.getPhone());
            }
            if (StringUtils.isNoneBlank(userUpdateRequestDTO.getUsername())) {
                user.setUsername(userUpdateRequestDTO.getUsername());
            }

            return (T) user;
        } else if (entity instanceof Client && requestDTO instanceof ClientRequestDTO) {
            Client client = (Client) entity;
            ClientRequestDTO clientRequestDTO = (ClientRequestDTO) requestDTO;

            if (StringUtils.isNoneBlank(clientRequestDTO.getFirstName())) {
                client.setFirstName(clientRequestDTO.getFirstName());
            }
            if (StringUtils.isNoneBlank(clientRequestDTO.getLastName())) {
                client.setLastName(clientRequestDTO.getLastName());
            }
            if (StringUtils.isNoneBlank(clientRequestDTO.getEmail())) {
                client.setEmail(clientRequestDTO.getEmail());
            }
            if (StringUtils.isNoneBlank(clientRequestDTO.getPhone())) {
                client.setPhone(clientRequestDTO.getPhone());
            }

            return (T) client;
        } else {
            throw new IllegalArgumentException("Invalid entity or requestDTO types.");
        }
    }

    public static String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }


    //    public User checkingForUpdatesUser(User user, UserUpdateRequestDTO userUpdateRequestDTO) {
//        if (StringUtils.isNoneBlank(userUpdateRequestDTO.getFirstName())) {
//            user.setFirstName(userUpdateRequestDTO.getFirstName());
//        }
//        if (StringUtils.isNoneBlank(userUpdateRequestDTO.getLastName())) {
//            user.setLastName(userUpdateRequestDTO.getLastName());
//        }
//        if (StringUtils.isNoneBlank(userUpdateRequestDTO.getPhone())) {
//            user.setPhone(userUpdateRequestDTO.getPhone());
//        }
//        if (StringUtils.isNoneBlank(userUpdateRequestDTO.getUsername())) {
//            user.setUsername(userUpdateRequestDTO.getUsername());
//        }
//        return user;
//    }
//
//    public Client checkingForUpdatesClient(Client client, ClientRequestDTO clientRequestDTO) {
//        if (StringUtils.isNoneBlank(clientRequestDTO.getFirstName())) {
//            client.setFirstName(clientRequestDTO.getFirstName());
//        }
//        if (StringUtils.isNoneBlank(clientRequestDTO.getLastName())) {
//            client.setLastName(clientRequestDTO.getLastName());
//        }
//        if (StringUtils.isNoneBlank(clientRequestDTO.getEmail())) {
//            client.setEmail(clientRequestDTO.getEmail());
//        }
//        if (StringUtils.isNoneBlank(clientRequestDTO.getPhone())) {
//            client.setPhone(clientRequestDTO.getPhone());
//        }
//        return client;
//    }
}
