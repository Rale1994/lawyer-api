package com.laywerapi.laywerapi.entity;

import com.laywerapi.laywerapi.dto.request.RegisterUserRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.List;

@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Audited
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
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<Trial> trials;

    public User(RegisterUserRequestDTO registerUserRequestDTO) {
        this.firstName = registerUserRequestDTO.getFirstName();
        this.lastName = registerUserRequestDTO.getLastName();
        this.email = registerUserRequestDTO.getEmail();
        this.username = registerUserRequestDTO.getUsername();
        this.phone = registerUserRequestDTO.getPhone();
        this.role = "USER";
    }

}
