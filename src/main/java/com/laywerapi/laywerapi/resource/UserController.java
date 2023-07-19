package com.laywerapi.laywerapi.resource;

import com.laywerapi.laywerapi.dto.request.UserUpdateRequestDTO;
import com.laywerapi.laywerapi.dto.response.UserResponseDTO;
import com.laywerapi.laywerapi.dto.response.UserUpdatedResponseDTO;
import com.laywerapi.laywerapi.entity.UserRegistrationDetails;
import com.laywerapi.laywerapi.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/users")
@Api
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String home() {
        return "Hello";
    }

    @PutMapping("/update")
    public UserUpdatedResponseDTO update(@AuthenticationPrincipal UserRegistrationDetails loggedUser, @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) throws Exception {
        return userService.findUserForUpdate(loggedUser, userUpdateRequestDTO);
    }

    @GetMapping("/all")
    public List<UserResponseDTO> getAllUsers() {
        return userService.findAll();
    }
}
