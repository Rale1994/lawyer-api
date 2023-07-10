package com.laywerapi.laywerapi.services.implementation;

import com.laywerapi.laywerapi.dto.request.ClientRequestDTO;
import com.laywerapi.laywerapi.dto.response.ClientResponseDTO;
import com.laywerapi.laywerapi.entity.Client;
import com.laywerapi.laywerapi.entity.CustomUserDetails;
import com.laywerapi.laywerapi.entity.User;
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
import java.util.Set;
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
    public void addClient(CustomUserDetails loggedUser, ClientRequestDTO clientRequestDTO) throws Exception {
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
        Optional<User> user = userRepository.findById(loggedUser.getId());
        Client savedClient = new Client(clientRequestDTO, user.get());
        clientRepository.save(savedClient);
    }

    @Override
    public List<ClientResponseDTO> allClients(CustomUserDetails loggedUser) {
        log.info("Getting all clients...");
        List<Client> clients = (List<Client>) clientRepository.findAll();
        return clients.stream().filter(client -> Objects.equals(client.getUserId().getId(), loggedUser.getId())).map(ClientResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<ClientResponseDTO> getOneByFirstName(CustomUserDetails loggedUser, String clientName) {
        log.info("Getting one client by first name");
        List<Client> clients = (List<Client>) clientRepository.findAll();
        return clients.stream()
                .filter(client -> Objects.equals(client.getUserId().getId(), loggedUser.getId()))
                .filter(client -> Objects.equals(client.getFirstName(), clientName.toUpperCase()))
                .map(ClientResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ClientResponseDTO updateClient(CustomUserDetails loggedUser, ClientRequestDTO clientRequestDTO, Long clientId) {
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
        clientRepository.save(client);

        return new ClientResponseDTO(client);
    }

    @Override
    public void deleteClient(CustomUserDetails loggedUser, Long clientId) {
        List<Client> clients = (List<Client>) clientRepository.findAll();
        clients.stream()
                .filter(client -> Objects.equals(client.getUserId().getId(), loggedUser.getId()))
                .forEach(client -> {
                    if (clientRepository.existsById(clientId)) {
                        clientRepository.deleteById(clientId);
                    } else {
                        throw new ApiRequestException("You do not have this client!");
                    }
                });
    }
}
