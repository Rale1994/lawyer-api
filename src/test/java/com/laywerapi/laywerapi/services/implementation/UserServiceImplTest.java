package com.laywerapi.laywerapi.services.implementation;

import com.laywerapi.laywerapi.dto.response.UserResponseDTO;
import com.laywerapi.laywerapi.dto.response.UserUpdatedResponseDTO;
import com.laywerapi.laywerapi.entity.User;
import com.laywerapi.laywerapi.entity.UserRegistrationDetails;
import com.laywerapi.laywerapi.exception.ApiRequestException;
import com.laywerapi.laywerapi.repositories.UserRepository;
import com.laywerapi.laywerapi.shared.Utils;
import com.laywerapi.laywerapi.utils.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    Utils utils;
    @InjectMocks
    private UserServiceImpl userServiceImpl;


    @Test
    void testCreateAccountWithoutUserAddRequestDTO() {
        // GIVEN
        var userAddRequestDTO = TestUtil.createUserAddRequestDTO();
        userAddRequestDTO.setEmail(null);
        var user = new User(userAddRequestDTO);
        user.setId(12346L);

        // WHEN
        when(userRepository.findByEmail(userAddRequestDTO.getEmail())).thenReturn(Optional.ofNullable(user));

        // THEN
        assertThrows(ApiRequestException.class, () -> userServiceImpl.createAccount(userAddRequestDTO));
    }

    @Test
    void testUpdateLawyerAccount() throws Exception {
        // GIVEN
        var user = TestUtil.createUser();
        //var loggedUser = new CustomUserDetails(user);
        var newLogged=new UserRegistrationDetails(user);
        var userUpdateRequestDTO = TestUtil.createUserUpdateRequestDTO();
        var updatedUser = TestUtil.createUpdatedUser();

        // WHEN
        when(userRepository.findByUsername(newLogged.getUsername())).thenReturn(Optional.of(user));
        when(utils.checkingForUpdatesUser(user, userUpdateRequestDTO)).thenReturn(updatedUser);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);


        // ACTION
        UserUpdatedResponseDTO userUpdatedResponseDTO = userServiceImpl.findUserForUpdate(newLogged, userUpdateRequestDTO);

        // ASSERT
        assertNotNull(userUpdatedResponseDTO);
        assertEquals(userUpdateRequestDTO.getFirstName(), updatedUser.getFirstName());
    }

    @Test
    void testUpdateLawyerAccountIfUsernameDoesNotExist() {
        // GIVEN
        var user = TestUtil.createUser();
//        var loggedUser = new CustomUserDetails(user);
        var newLogged=new UserRegistrationDetails(user);
        var userUpdateRequestDTO = TestUtil.createUserUpdateRequestDTO();
        var updatedUser = TestUtil.createUpdatedUser();

        // WHEN
        when(userRepository.findByUsername(newLogged.getUsername())).thenReturn(Optional.empty());

        // THEN
        assertThrows(ApiRequestException.class, () -> userServiceImpl.findUserForUpdate(newLogged, userUpdateRequestDTO));
    }

    @Test
    void testGettingAllLawyerAccounts() {
        // GIVEN
        List<User> users = TestUtil.createUserList();

        // WHEN
        when(userRepository.findAll()).thenReturn(users);

        // ACTION
        userServiceImpl.findAll();

        // THEN
        List<UserResponseDTO> userResponseDTOS = users.stream().map(UserResponseDTO::new).collect(Collectors.toList());

        assertNotNull(userResponseDTOS);
    }
}