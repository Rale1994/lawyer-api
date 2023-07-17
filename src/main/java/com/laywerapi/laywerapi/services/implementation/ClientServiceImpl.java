package com.laywerapi.laywerapi.services.implementation;

import com.laywerapi.laywerapi.dto.request.ClientRequestDTO;
import com.laywerapi.laywerapi.dto.response.ClientResponseDTO;
import com.laywerapi.laywerapi.entity.Client;
import com.laywerapi.laywerapi.entity.User;
import com.laywerapi.laywerapi.entity.UserT;
import com.laywerapi.laywerapi.exception.ApiRequestException;
import com.laywerapi.laywerapi.repositories.ClientRepository;
import com.laywerapi.laywerapi.repositories.UserRepository;
import com.laywerapi.laywerapi.services.ClientService;
import com.laywerapi.laywerapi.shared.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final Utils utils;

    public ClientServiceImpl(ClientRepository clientRepository, UserRepository userRepository, Utils utils) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.utils = utils;
    }

    @Override
    public ClientResponseDTO addClient(User loggedUser, ClientRequestDTO clientRequestDTO) throws Exception {
        log.info("Adding client...");
        List<Client> clients = clientRepository.findByEmail(clientRequestDTO.getEmail());
        if (!clients.isEmpty()) {
            log.info("We have some client with this email");
            for (Client client : clients) {
                if (client.getUserId().getId().equals(loggedUser.getId())) {
                    log.info("Lawyer already has this client!");
                    throw new ApiRequestException("You already have this client!");
                }
            }
        }
        Optional<UserT> userOptional = userRepository.findById(loggedUser.getId());
        if (userOptional.isEmpty()) {
            throw new ApiRequestException("Wrong login credential!");
        }
        UserT user = userOptional.get();
        Client client = new Client(clientRequestDTO, user);
        Client savedClient = clientRepository.save(client);
        return new ClientResponseDTO(savedClient);
    }

    @Override
    public List<ClientResponseDTO> allClients(User loggedUser) {
        log.info("Getting all clients...");
        List<Client> clients = (List<Client>) clientRepository.findAll();
        return clients.stream().filter(client -> Objects.equals(client.getUserId().getId(), loggedUser.getId())).map(ClientResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<ClientResponseDTO> getOneByFirstName(User loggedUser, String clientName) {
        log.info("Getting one client by first name");
        List<Client> clients = (List<Client>) clientRepository.findAll();
        List<ClientResponseDTO> clientResponseDTOS = clients.stream()
                .filter(client -> Objects.equals(client.getUserId().getId(), loggedUser.getId()))
                .filter(client -> Objects.equals(client.getFirstName(), clientName.toUpperCase()))
                .map(ClientResponseDTO::new)
                .collect(Collectors.toList());
        if (clientResponseDTOS.isEmpty()) {
            log.warn("Getting client who doesn't exist for this user!");
            throw new ApiRequestException("You don't have this client!");
        } else {
            return clientResponseDTOS;
        }
    }

    @Override
    public ClientResponseDTO updateClient(User loggedUser, ClientRequestDTO clientRequestDTO, Long clientId) {
        log.info("Updating client information!");
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isEmpty()) {
            log.error("Trying to delete user with id: {} who don't exist!", clientId);
            throw new ApiRequestException("Client does not exist!");
        }
        if (clientOptional.get().getUserId().getId() != loggedUser.getId()) {
            log.error("Client with id: {} who don't exist for this lawyer!", clientId);
            throw new ApiRequestException("You do not have this client!");
        }
        log.info("Checking for updates fields...");
        Client client = utils.checkingForUpdatesClient(clientOptional.get(), clientRequestDTO);
        Client updatedClient = clientRepository.save(client);

        return new ClientResponseDTO(updatedClient);
    }

    @Override
    public void deleteClient(User loggedUser, Long clientId) {
        log.info("Deleting client...");
        List<Client> clients = (List<Client>) clientRepository.findAll();
        clients.stream().filter(client -> Objects.equals(client.getUserId().getId(), loggedUser.getId()))
                .filter(client -> Objects.equals(client.getId(), clientId))
                .forEach(client -> clientRepository.deleteById(client.getId()));
    }
}
