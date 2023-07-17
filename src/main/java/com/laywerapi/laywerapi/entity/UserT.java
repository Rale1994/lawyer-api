//package com.laywerapi.laywerapi.entity;
//
//import com.laywerapi.laywerapi.dto.request.UserAddRequestDTO;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import javax.persistence.*;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode
//public class UserT implements UserDetails {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//    private String firstName;
//    private String lastName;
//    private String email;
//    private String phone;
//    private String username;
//    private String password;
//    private String role;
//    private Boolean locked = false;
//    private Boolean enabled = false;
//    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
//    private List<Client> clients;
//
//    public UserT(UserAddRequestDTO userAddRequestDTO) {
//        this.firstName = userAddRequestDTO.getFirstName();
//        this.lastName = userAddRequestDTO.getLastName();
//        this.email = userAddRequestDTO.getEmail();
//        this.username = userAddRequestDTO.getUsername();
//        this.phone = userAddRequestDTO.getPhone();
//        this.role = "ROLE_USER";
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        SimpleGrantedAuthority authority =
//                new SimpleGrantedAuthority(role);
//        return Collections.singletonList(authority);
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return !locked;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return enabled;
//    }
//}
