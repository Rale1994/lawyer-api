package com.laywerapi.laywerapi.services;

import com.laywerapi.laywerapi.dto.request.UserAddRequestDTO;

public interface RegistrationService {
    String register(UserAddRequestDTO userAddRequestDTO);
}
