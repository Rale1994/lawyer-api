package com.laywerapi.laywerapi.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ClientRequestDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private Long idNumber;
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
}
