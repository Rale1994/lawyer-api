package com.laywerapi.laywerapi.services.implementation;

//import com.laywerapi.laywerapi.entity.CustomUserDetails;
import com.laywerapi.laywerapi.entity.UserT;
import com.laywerapi.laywerapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
//@Service
//public class UserDetailServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private PasswordEncoder passwordEncoder;


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        Optional<UserT> userEntityOp = userRepository.findByUsername(username);
//
//        if (!userEntityOp.isPresent()) throw new UsernameNotFoundException(username);
//
//        UserT user = userEntityOp.get();
//        return new CustomUserDetails(user);
//    }

//    public String signUpUser(CustomUserDetails customUserDetails) {
//        boolean userExists = userRepository.findByEmail(customUserDetails.getEmail())
//                .isPresent();
//        if (userExists) {
//            throw new ApiRequestException("Email already taken!");
//        }
//        String ecnodedPassword=passwordEncoder.encode(customUserDetails.getPassword());
//        customUserDetails.getU
//        return "";
//    }
//}
