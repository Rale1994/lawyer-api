package com.laywerapi.laywerapi.services.implementation;

import com.laywerapi.laywerapi.dto.request.RegisterUserRequestDTO;
import com.laywerapi.laywerapi.dto.response.UserResponseDTO;
import com.laywerapi.laywerapi.dto.response.UserUpdatedResponseDTO;
import com.laywerapi.laywerapi.entity.User;
import com.laywerapi.laywerapi.entity.UserRegistrationDetails;
import com.laywerapi.laywerapi.entity.VerificationToken;
import com.laywerapi.laywerapi.exception.ApiRequestException;
import com.laywerapi.laywerapi.repositories.UserRepository;
import com.laywerapi.laywerapi.repositories.VerificationTokenRepository;
import com.laywerapi.laywerapi.shared.Utils;
import com.laywerapi.laywerapi.utils.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private VerificationTokenRepository verificationTokenRepository;
    @Mock
    Utils utils;
    @InjectMocks
    private UserServiceImpl userServiceImpl;


    @Test
    void testRegisterUser() {
        // GIVEN
        RegisterUserRequestDTO registerUserRequestDTO = TestUtil.createUserAddRequestDTO();
        User user = TestUtil.newCreateUser();

        // WHEN
        when(userRepository.findByEmail(registerUserRequestDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerUserRequestDTO.getPassword())).thenReturn("password");
        when(userRepository.save(user)).thenReturn(user);

        // ACTION
        User actual = userServiceImpl.registerUser(registerUserRequestDTO);

        // THEN
        assertNotNull(actual);
        assertEquals("FIRSTNAME", actual.getFirstName());
    }

    @Test
    void testRegisterUserWithEmailWhichAlreadyExist() {
        // GIVEN
        var registerUserRequestDTO = TestUtil.createUserAddRequestDTO();
        var user = new User(registerUserRequestDTO);
        user.setId(12346L);

        // WHEN
        when(userRepository.findByEmail(registerUserRequestDTO.getEmail())).thenReturn(Optional.of(user));

        // THEN
        assertThrows(ApiRequestException.class, () -> userServiceImpl.registerUser(registerUserRequestDTO));
    }


    @Test
    void testUpdateLawyerAccount() throws Exception {
        // GIVEN
        var user = TestUtil.createUser();
        //var loggedUser = new CustomUserDetails(user);
        var newLogged = new UserRegistrationDetails(user);
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
        var newLogged = new UserRegistrationDetails(user);
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

//    @Test
//    void testCreateUserVerificationToken() {
//        // GIVEN
//        var user = TestUtil.createUser();
//        String token = "token-verification";
//        var verificationToken = new VerificationToken(token, user);
//
//        // WHEN
//        when(verificationTokenRepository.save(verificationToken)).thenReturn(verificationToken);
//
//        // ACTION
//        userServiceImpl.saveUserVerificationToken(user, token);
//    }

    @Test
    void testValidateVerificationToken() {
        // GIVEN
        String token = "token";
        var user = TestUtil.createUser();
        var verificationToken = TestUtil.createVerificationToken(token, user);

        // WHEN
        when(verificationTokenRepository.findByToken(token)).thenReturn(verificationToken);

        // ACTION
        String validToken = userServiceImpl.validateToken(token);

        // ASSERT
        assertEquals(validToken, "valid");
    }

    @Test
    void testInvalidVerificationToken() {
        // GIVEN
        String token = "token";
        var user = TestUtil.createUser();
        var verificationToken = TestUtil.createVerificationToken(token, user);

        // WHEN
        when(verificationTokenRepository.findByToken(token)).thenReturn(null);

        // ACTION
        String invalidToken = userServiceImpl.validateToken(token);

        // ASSERT
        assertEquals(invalidToken, "Invalid verification token!");
    }

    @Test
    void testVerificationTokenExpired() {
        // GIVEN
        String token = "token";
        var user = TestUtil.createUser();
        var verificationToken = TestUtil.createVerificationToken(token, user);
        Date expiredDate = TestUtil.addHoursToJavaUtilDate(verificationToken.getExpirationTime());
        verificationToken.setExpirationTime(expiredDate);

        // WHEN
        when(verificationTokenRepository.findByToken(token)).thenReturn(verificationToken);


        // ACTION
        String invalidToken = userServiceImpl.validateToken(token);

        // ASSERT
        assertEquals(invalidToken, "Token already expired!");

        verify(verificationTokenRepository, times(1)).delete(verificationToken);
    }
}