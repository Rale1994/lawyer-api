package com.laywerapi.laywerapi.services.implementation;

import com.laywerapi.laywerapi.dto.request.UserAddRequestDTO;
import com.laywerapi.laywerapi.dto.response.UserResponseDTO;
import com.laywerapi.laywerapi.dto.response.UserUpdatedResponseDTO;
import com.laywerapi.laywerapi.entity.User;
import com.laywerapi.laywerapi.entity.UserT;
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
    void testCreateUserAccount() throws Exception {
        // GIVEN
        UserAddRequestDTO userAddRequestDTO = TestUtil.createUserAddRequestDTO();
        UserT user = TestUtil.newCreateUser();

        // WHEN
        when(userRepository.findByEmail(userAddRequestDTO.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(userAddRequestDTO.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userAddRequestDTO.getPassword())).thenReturn("password");
        when(userRepository.save(user)).thenReturn(user);

        // ACTION
        UserResponseDTO actual = userServiceImpl.createAccount(userAddRequestDTO);

        // THEN
        assertNotNull(actual);
        assertEquals("FIRSTNAME", actual.getFirstName());
    }

    @Test
    void testCreateUserAccountWithEmailWhichAlreadyExist() {
        // GIVEN
        var userAddRequestDTO = TestUtil.createUserAddRequestDTO();
        var user = new UserT(userAddRequestDTO);
        user.setId(12346L);

        // WHEN
        when(userRepository.findByEmail(userAddRequestDTO.getEmail())).thenReturn(Optional.of(user));

        // THEN
        assertThrows(ApiRequestException.class, () -> userServiceImpl.createAccount(userAddRequestDTO));
    }

    @Test
    void testCreateUserAccountWithUsernameWhichAlreadyExist() {
        // GIVEN
        var userAddRequestDTO = TestUtil.createUserAddRequestDTO();
        var user = new UserT(userAddRequestDTO);
        user.setId(12346L);

        // WHEN
        when(userRepository.findByUsername(userAddRequestDTO.getUsername())).thenReturn(Optional.of(user));

        // THEN
        assertThrows(ApiRequestException.class, () -> userServiceImpl.createAccount(userAddRequestDTO));
    }

    @Test
    void testCreateAccountWithoutUserAddRequestDTO() {
        // GIVEN
        var userAddRequestDTO = TestUtil.createUserAddRequestDTO();
        userAddRequestDTO.setEmail(null);
        var user = new UserT(userAddRequestDTO);
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
        var loggedUser = new User(user);
        var userUpdateRequestDTO = TestUtil.createUserUpdateRequestDTO();
        var updatedUser = TestUtil.createUpdatedUser();

        // WHEN
        when(userRepository.findByUsername(loggedUser.getUsername())).thenReturn(Optional.of(user));
        when(utils.checkingForUpdatesUser(user, userUpdateRequestDTO)).thenReturn(updatedUser);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);


        // ACTION
        UserUpdatedResponseDTO userUpdatedResponseDTO = userServiceImpl.findUserForUpdate(loggedUser, userUpdateRequestDTO);

        // ASSERT
        assertNotNull(userUpdatedResponseDTO);
        assertEquals(userUpdateRequestDTO.getFirstName(), updatedUser.getFirstName());
    }

    @Test
    void testUpdateLawyerAccountIfUsernameDoesNotExist() {
        // GIVEN
        var user = TestUtil.createUser();
        var loggedUser = new User(user);
        var userUpdateRequestDTO = TestUtil.createUserUpdateRequestDTO();
        var updatedUser = TestUtil.createUpdatedUser();

        // WHEN
        when(userRepository.findByUsername(loggedUser.getUsername())).thenReturn(Optional.empty());

        // THEN
        assertThrows(ApiRequestException.class, () -> userServiceImpl.findUserForUpdate(loggedUser, userUpdateRequestDTO));
    }

    @Test
    void testGettingAllLawyerAccounts() {
        // GIVEN
        List<UserT> users = TestUtil.createUserList();

        // WHEN
        when(userRepository.findAll()).thenReturn(users);

        // ACTION
        userServiceImpl.findAll();

        // THEN
        List<UserResponseDTO> userResponseDTOS = users.stream().map(UserResponseDTO::new).collect(Collectors.toList());

        assertNotNull(userResponseDTOS);
    }
}