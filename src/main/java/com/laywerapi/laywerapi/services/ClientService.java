package com.laywerapi.laywerapi.services;

import com.laywerapi.laywerapi.dto.request.ClientRequestDTO;
import com.laywerapi.laywerapi.dto.response.ClientResponseDTO;
import com.laywerapi.laywerapi.entity.User;

import java.util.List;

public interface ClientService {
    ClientResponseDTO addClient(User loggedUser, ClientRequestDTO clientRequestDTO) throws Exception;

    List<ClientResponseDTO> allClients(User loggedUser);

    List<ClientResponseDTO>  getOneByFirstName(User loggedUser, String clientName);

    ClientResponseDTO updateClient(User loggedUser, ClientRequestDTO clientRequestDTO, Long clientId);

    void deleteClient(User loggedUser, Long clientId);
}
