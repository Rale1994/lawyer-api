package com.laywerapi.laywerapi.resource;

import com.laywerapi.laywerapi.dto.request.UserAddRequestDTO;
import com.laywerapi.laywerapi.dto.request.UserUpdateRequestDTO;
import com.laywerapi.laywerapi.dto.response.UserResponseDTO;
import com.laywerapi.laywerapi.dto.response.UserUpdatedResponseDTO;
import com.laywerapi.laywerapi.entity.CustomUserDetails;
import com.laywerapi.laywerapi.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update")
    public UserUpdatedResponseDTO update(@AuthenticationPrincipal CustomUserDetails loggedUser, @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) throws Exception {
        UserUpdatedResponseDTO userUpdatedResponseDTO = userService.findUserForUpdate(loggedUser, userUpdateRequestDTO);
        return userUpdatedResponseDTO;
    }

    @PostMapping("/create")
    public void createAccount(@RequestBody UserAddRequestDTO userAddRequestDTO) throws Exception {
        userService.createAccount(userAddRequestDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public List<UserResponseDTO> getAllUsers() {
        return userService.findAll();
    }
}
