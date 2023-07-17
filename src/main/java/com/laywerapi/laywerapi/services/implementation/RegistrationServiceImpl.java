package com.laywerapi.laywerapi.services.implementation;

import com.laywerapi.laywerapi.dto.request.UserAddRequestDTO;
import com.laywerapi.laywerapi.exception.ApiRequestException;
import com.laywerapi.laywerapi.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final EmailValidator emailValidator;
//    private final UserDetailServiceImpl userDetailServiceImpl;

    @Override
    public String register(UserAddRequestDTO userAddRequestDTO) {
     return "it works!";
    }
}
