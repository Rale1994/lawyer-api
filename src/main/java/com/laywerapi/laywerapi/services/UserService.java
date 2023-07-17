package com.laywerapi.laywerapi.services;

import com.laywerapi.laywerapi.dto.request.UserAddRequestDTO;
import com.laywerapi.laywerapi.dto.request.UserUpdateRequestDTO;
import com.laywerapi.laywerapi.dto.response.UserResponseDTO;
import com.laywerapi.laywerapi.dto.response.UserUpdatedResponseDTO;
import com.laywerapi.laywerapi.entity.User;
import com.laywerapi.laywerapi.entity.User;

import java.util.List;

public interface UserService {
    UserResponseDTO createAccount(UserAddRequestDTO userAddRequestDTO) throws Exception;

    UserUpdatedResponseDTO findUserForUpdate(User loggedUser, UserUpdateRequestDTO userUpdateRequestDTO) throws Exception;

    List<UserResponseDTO> findAll();

}
