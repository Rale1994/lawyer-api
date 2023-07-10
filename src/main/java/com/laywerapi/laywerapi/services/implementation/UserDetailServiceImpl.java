package com.laywerapi.laywerapi.services.implementation;

import com.laywerapi.laywerapi.entity.CustomUserDetails;
import com.laywerapi.laywerapi.entity.User;
import com.laywerapi.laywerapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userEntityOp = userRepository.findByUsername(username);

        if (!userEntityOp.isPresent()) throw new UsernameNotFoundException(username);

        User user = userEntityOp.get();
        return new CustomUserDetails(user);
    }
}
