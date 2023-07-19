package com.laywerapi.laywerapi.services.implementation;

import com.laywerapi.laywerapi.dto.request.RegisterUserRequestDTO;
import com.laywerapi.laywerapi.dto.request.UserUpdateRequestDTO;
import com.laywerapi.laywerapi.dto.response.UserResponseDTO;
import com.laywerapi.laywerapi.dto.response.UserUpdatedResponseDTO;
import com.laywerapi.laywerapi.entity.User;
import com.laywerapi.laywerapi.entity.UserRegistrationDetails;
import com.laywerapi.laywerapi.entity.VerificationToken;
import com.laywerapi.laywerapi.exception.ApiRequestException;
import com.laywerapi.laywerapi.repositories.UserRepository;
import com.laywerapi.laywerapi.repositories.VerificationTokenRepository;
import com.laywerapi.laywerapi.services.UserService;
import com.laywerapi.laywerapi.shared.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final Utils utils;

    public UserServiceImpl(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, PasswordEncoder passwordEncoder, Utils utils) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.utils = utils;
    }


    @Override
    public UserResponseDTO createAccount(RegisterUserRequestDTO registerUserRequestDTO) throws Exception {
        log.info("Creating account...");
        Optional<User> userEmail = userRepository.findByEmail(registerUserRequestDTO.getEmail());
        Optional<User> userUsername = userRepository.findByUsername(registerUserRequestDTO.getUsername());
        if (userEmail.isPresent() || userUsername.isPresent()) {
            throw new ApiRequestException("User already exist");
        }
        User user = new User(registerUserRequestDTO);
        user.setPassword(passwordEncoder.encode(registerUserRequestDTO.getPassword()));
        User savedUser = userRepository.save(user);
        return new UserResponseDTO(savedUser);
    }

    @Override
    public UserUpdatedResponseDTO findUserForUpdate(UserRegistrationDetails loggedUser, UserUpdateRequestDTO userUpdateRequestDTO) throws Exception {
        log.info("Updating account...");
        Optional<User> optionalUser = userRepository.findByUsername(loggedUser.getUsername());
        if (optionalUser.isEmpty()) {
            throw new ApiRequestException("User with this username does not exist");
        }
        User user = optionalUser.get();
        log.info("Checking for update fields");
        User userForUpdate = utils.checkingForUpdatesUser(user, userUpdateRequestDTO);
        userRepository.save(userForUpdate);

        return new UserUpdatedResponseDTO(userForUpdate);
    }

    @Override
    public List<UserResponseDTO> findAll() {
        log.info("Finding all users...");
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream().map(UserResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public User registerUser(RegisterUserRequestDTO registerUserRequestDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(registerUserRequestDTO.getEmail());
        if (optionalUser.isPresent()) {
            throw new ApiRequestException("User with email" + registerUserRequestDTO.getEmail() + " already exists!");
        }
        User user = new User(registerUserRequestDTO);
        user.setPassword(passwordEncoder.encode(registerUserRequestDTO.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public void saveUserVerificationToken(User user, String verificationToken) {
        VerificationToken verificationTokenNew = new VerificationToken(verificationToken, user);
        verificationTokenRepository.save(verificationTokenNew);
    }

    @Override
    public String validateToken(String verificationToken) {
        VerificationToken token = verificationTokenRepository.findByToken(verificationToken);
        if (token == null) {
            return "Invalid verification token!";
        }
        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(token);
            return "Token already expired!";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }
}
