package com.laywerapi.laywerapi.entity;

import com.laywerapi.laywerapi.dto.request.ClientRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.List;

@Entity(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private Long idNumber;
    private String email;
    private String phone;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User userId;
    @OneToMany(mappedBy = "clientId", cascade = CascadeType.ALL)

    private List<Trial> trials;

    public Client(ClientRequestDTO clientRequestDTO, User user) {
        this.firstName = clientRequestDTO.getFirstName().toUpperCase();
        this.lastName = clientRequestDTO.getLastName().toUpperCase();
        this.idNumber=clientRequestDTO.getIdNumber();
        this.email = clientRequestDTO.getEmail();
        this.phone = clientRequestDTO.getPhone();
        this.userId = user;
    }

}
