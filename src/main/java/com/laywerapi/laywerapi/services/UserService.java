package com.laywerapi.laywerapi.services;

import com.laywerapi.laywerapi.dto.request.RegisterUserRequestDTO;
import com.laywerapi.laywerapi.dto.request.UserUpdateRequestDTO;
import com.laywerapi.laywerapi.dto.response.UserResponseDTO;
import com.laywerapi.laywerapi.dto.response.UserUpdatedResponseDTO;
import com.laywerapi.laywerapi.entity.User;
import com.laywerapi.laywerapi.entity.UserRegistrationDetails;

import java.util.List;

public interface UserService {
//    UserResponseDTO createAccount(RegisterUserRequestDTO registerUserRequestDTO) throws Exception;

    UserUpdatedResponseDTO findUserForUpdate(UserRegistrationDetails loggedUser, UserUpdateRequestDTO userUpdateRequestDTO) throws Exception;

    List<UserResponseDTO> findAll();

    User registerUser(RegisterUserRequestDTO registerUserRequestDTO);

    void saveUserVerificationToken(User user, String verificationToken);

    String validateToken(String verificationToken);
}
