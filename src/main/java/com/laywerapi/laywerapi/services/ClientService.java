package com.laywerapi.laywerapi.services;

import com.laywerapi.laywerapi.dto.request.ClientRequestDTO;
import com.laywerapi.laywerapi.dto.response.ClientResponseDTO;
import com.laywerapi.laywerapi.entity.CustomUserDetails;

import java.util.List;

public interface ClientService {
    ClientResponseDTO addClient(CustomUserDetails loggedUser, ClientRequestDTO clientRequestDTO) throws Exception;

    List<ClientResponseDTO> allClients(CustomUserDetails loggedUser);

    List<ClientResponseDTO>  getOneByFirstName(CustomUserDetails loggedUser, String clientName);

    ClientResponseDTO updateClient(CustomUserDetails loggedUser, ClientRequestDTO clientRequestDTO, Long clientId);

    void deleteClient(CustomUserDetails loggedUser, Long clientId);
}
