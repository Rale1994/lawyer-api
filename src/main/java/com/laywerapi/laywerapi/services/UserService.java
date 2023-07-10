package com.laywerapi.laywerapi.services;

import com.laywerapi.laywerapi.dto.request.UserAddRequestDTO;
import com.laywerapi.laywerapi.dto.request.UserUpdateRequestDTO;
import com.laywerapi.laywerapi.dto.response.UserResponseDTO;
import com.laywerapi.laywerapi.dto.response.UserUpdatedResponseDTO;
import com.laywerapi.laywerapi.entity.CustomUserDetails;

import java.util.List;

public interface UserService {
    void createAccount(UserAddRequestDTO userAddRequestDTO) throws Exception;
    UserUpdatedResponseDTO findUserForUpdate(CustomUserDetails loggedUser, UserUpdateRequestDTO userUpdateRequestDTO) throws Exception;

    List<UserResponseDTO> findAll();
}
