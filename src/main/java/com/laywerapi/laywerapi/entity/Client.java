package com.laywerapi.laywerapi.entity;

import com.laywerapi.laywerapi.dto.request.ClientRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private Long idNumber;
    private String email;
    private String phone;
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserT userId;

    public Client(ClientRequestDTO clientRequestDTO, UserT user) {
        this.firstName = clientRequestDTO.getFirstName().toUpperCase();
        this.lastName = clientRequestDTO.getLastName().toUpperCase();
        this.idNumber=clientRequestDTO.getIdNumber();
        this.email = clientRequestDTO.getEmail();
        this.phone = clientRequestDTO.getPhone();
        this.userId = user;
    }

}
