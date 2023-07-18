package com.laywerapi.laywerapi.services;

import com.laywerapi.laywerapi.dto.request.ClientRequestDTO;
import com.laywerapi.laywerapi.dto.response.ClientResponseDTO;
import com.laywerapi.laywerapi.entity.CustomUserDetails;
import com.laywerapi.laywerapi.entity.UserRegistrationDetails;
import com.laywerapi.laywerapi.services.implementation.UserRegistrationDetailsImpl;

import java.util.List;

public interface ClientService {
    ClientResponseDTO addClient(UserRegistrationDetails loggedUser, ClientRequestDTO clientRequestDTO) throws Exception;

    List<ClientResponseDTO> allClients(UserRegistrationDetails loggedUser);

    List<ClientResponseDTO>  getOneByFirstName(UserRegistrationDetails loggedUser, String clientName);

    ClientResponseDTO updateClient(UserRegistrationDetails loggedUser, ClientRequestDTO clientRequestDTO, Long clientId);

    void deleteClient(UserRegistrationDetails loggedUser, Long clientId);
}
