package com.laywerapi.laywerapi.services.implementation;

import com.laywerapi.laywerapi.dto.request.UserAddRequestDTO;
import com.laywerapi.laywerapi.dto.request.UserUpdateRequestDTO;
import com.laywerapi.laywerapi.dto.response.UserResponseDTO;
import com.laywerapi.laywerapi.dto.response.UserUpdatedResponseDTO;
import com.laywerapi.laywerapi.entity.User;
import com.laywerapi.laywerapi.exception.ApiRequestException;
import com.laywerapi.laywerapi.repositories.UserRepository;
import com.laywerapi.laywerapi.services.UserService;
import com.laywerapi.laywerapi.shared.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserDetailsService,UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Utils utils;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, Utils utils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.utils = utils;
    }

    @Override
    public UserResponseDTO createAccount(UserAddRequestDTO userAddRequestDTO) throws Exception {
        log.info("Creating account...");
        Optional<User> userEmail = userRepository.findByEmail(userAddRequestDTO.getEmail());
        Optional<User> userUsername = userRepository.findByUsername(userAddRequestDTO.getUsername());
        if (userEmail.isPresent() || userUsername.isPresent()) {
            throw new ApiRequestException("User already exist");
        }
        User user = new User(userAddRequestDTO);
        user.setPassword(passwordEncoder.encode(userAddRequestDTO.getPassword()));
        User savedUser = userRepository.save(user);
        return new UserResponseDTO(savedUser);
    }

    @Override
    public UserUpdatedResponseDTO findUserForUpdate(User loggedUser, UserUpdateRequestDTO userUpdateRequestDTO) throws Exception {
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userEntityOp = userRepository.findByUsername(username);

        if (userEntityOp.isEmpty()) throw new UsernameNotFoundException(username);

        User user = userEntityOp.get();
        return user;
    }
}
