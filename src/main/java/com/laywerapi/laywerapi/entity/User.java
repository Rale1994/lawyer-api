package com.laywerapi.laywerapi.entity;

import com.laywerapi.laywerapi.dto.request.UserAddRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String username;
    private String password;
    private String role;
    private boolean isEnabled = false;
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<Client> clients;

    public User(UserAddRequestDTO userAddRequestDTO) {
        this.firstName = userAddRequestDTO.getFirstName();
        this.lastName = userAddRequestDTO.getLastName();
        this.email = userAddRequestDTO.getEmail();
        this.username = userAddRequestDTO.getUsername();
        this.phone = userAddRequestDTO.getPhone();
        this.role = "USER";
    }

}
