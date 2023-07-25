package com.laywerapi.laywerapi.resource;

import com.laywerapi.laywerapi.dto.request.UserUpdateRequestDTO;
import com.laywerapi.laywerapi.dto.response.UserResponseDTO;
import com.laywerapi.laywerapi.dto.response.UserUpdatedResponseDTO;
import com.laywerapi.laywerapi.entity.UserRegistrationDetails;
import com.laywerapi.laywerapi.services.UserService;
import com.laywerapi.laywerapi.shared.Constants;
import io.swagger.annotations.Api;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(Constants.BASE_URL + "/users")
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
    public UserUpdatedResponseDTO update(@AuthenticationPrincipal UserRegistrationDetails loggedUser,
                                         @Valid  @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) throws Exception {
        return userService.findUserForUpdate(loggedUser, userUpdateRequestDTO);
    }

    @GetMapping("/all")
    public List<UserResponseDTO> getAllUsers() {
        return userService.findAll();
    }
}
