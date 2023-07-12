package com.laywerapi.laywerapi.dto.response;

import com.laywerapi.laywerapi.entity.Client;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ClientResponseDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String phone;

    public ClientResponseDTO(Client client){
        this.firstName=client.getFirstName();
        this.lastName=client.getLastName();
        this.email= client.getEmail();
        this.phone= client.getPhone();
    }
}
