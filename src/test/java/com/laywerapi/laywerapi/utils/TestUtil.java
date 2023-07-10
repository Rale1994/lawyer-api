package com.laywerapi.laywerapi.utils;

import com.laywerapi.laywerapi.dto.request.UserAddRequestDTO;
import com.laywerapi.laywerapi.dto.request.UserUpdateRequestDTO;
import com.laywerapi.laywerapi.dto.response.UserUpdatedResponseDTO;
import com.laywerapi.laywerapi.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {
    private static PasswordEncoder passwordEncoder;

    public static UserAddRequestDTO createUserAddRequestDTO() {
        UserAddRequestDTO userAddRequestDTO = new UserAddRequestDTO();
        userAddRequestDTO.setFirstName("FirstName");
        userAddRequestDTO.setLastName("LastName");
        userAddRequestDTO.setEmail("email@example.com");
        userAddRequestDTO.setPhone("+38145845687");
        userAddRequestDTO.setUsername("username");
        userAddRequestDTO.setPassword("password");

        return userAddRequestDTO;
    }

    public static User createUser() {
        User user = new User();
        user.setId(1234L);
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setEmail("email@example.com");
        user.setPhone("+38145845687");
        user.setUsername("username");
        user.setPassword("password");
        user.setRole("USER");

        return user;
    }

    public static User createUpdatedUser() {
        User user = new User();
        user.setId(1234L);
        user.setFirstName("FirstNameUpd");
        user.setLastName("LastNameUp");
        user.setEmail("updated@example.com");
        user.setPhone("+381458456872");
        user.setUsername("usernameUpdated");
        user.setPassword("password");
        user.setRole("USER");

        return user;
    }


    public static UserUpdateRequestDTO createUserUpdateRequestDTO() {
        UserUpdateRequestDTO userUpdateRequestDTO = new UserUpdateRequestDTO();
        userUpdateRequestDTO.setFirstName("FirstNameUpd");
        userUpdateRequestDTO.setLastName("LastNameUp");
        userUpdateRequestDTO.setEmail("updated@example.com");
        userUpdateRequestDTO.setPhone("381458456872");
        userUpdateRequestDTO.setUsername("usernameUpdated");

        return userUpdateRequestDTO;
    }

    public static UserUpdatedResponseDTO createUserUpdateResponseDTO() {
        UserUpdatedResponseDTO userUpdatedResponseDTO = new UserUpdatedResponseDTO();
        userUpdatedResponseDTO.setFirstName("FName1");
        userUpdatedResponseDTO.setLastName("LName1");
        userUpdatedResponseDTO.setPhone("+38145782154");
        userUpdatedResponseDTO.setEmail("update1@example.com");
        userUpdatedResponseDTO.setUsername("usernameNew");

        return userUpdatedResponseDTO;
    }

    public static List<User> createUserList() {
        List<User> users = new ArrayList<>();
        users.add(TestUtil.createUser());
        return users;
    }
}
