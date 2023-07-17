package com.laywerapi.laywerapi.dto.response;

import com.laywerapi.laywerapi.entity.User;
import com.laywerapi.laywerapi.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UserUpdatedResponseDTO {
    @NotNull(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Phone number is required")
    private String phone;

    public UserUpdatedResponseDTO(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.phone = user.getPhone();

    }
}
