package com.laywerapi.laywerapi.services.implementation;

import com.laywerapi.laywerapi.dto.request.TrialAddRequestDTO;
import com.laywerapi.laywerapi.dto.response.ClientResponseDTO;
import com.laywerapi.laywerapi.dto.response.TrialAddResponseDTO;
import com.laywerapi.laywerapi.dto.response.TrialResponseDTO;
import com.laywerapi.laywerapi.entity.Client;
import com.laywerapi.laywerapi.entity.Trial;
import com.laywerapi.laywerapi.entity.User;
import com.laywerapi.laywerapi.entity.UserRegistrationDetails;
import com.laywerapi.laywerapi.exception.ApiRequestException;
import com.laywerapi.laywerapi.repositories.ClientRepository;
import com.laywerapi.laywerapi.repositories.TrialRepository;
import com.laywerapi.laywerapi.repositories.UserRepository;
import com.laywerapi.laywerapi.services.TrialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrialServiceImpl implements TrialService {

    private final TrialRepository trialRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    @Override
    public TrialAddResponseDTO addTrial(UserRegistrationDetails loggedUser, TrialAddRequestDTO trialAddRequestDTO, Long clientId) {
        log.info("Adding trial, User: " + loggedUser.getUser().getFirstName() + " for clientId: " + clientId);
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isEmpty()) {
            log.warn("Client doesn't exist!");
            throw new ApiRequestException("Client with id " + clientId + " does not exist!");
        }
        Optional<User> userOptional = userRepository.findById(loggedUser.getId());
        User user = userOptional.get();
        Client client = clientOptional.get();
        ClientResponseDTO clientResponseDTO = new ClientResponseDTO(client);
        Trial trial = new Trial(trialAddRequestDTO, user, client);
        trialRepository.save(trial);
        return new TrialAddResponseDTO(trial, clientResponseDTO);
    }

    @Override
    public List<TrialResponseDTO> getAllClientTrials(UserRegistrationDetails loggedUser, Long clientId) {
        log.info("Getting all trials for current client...");
        List<Trial> trials = (List<Trial>) trialRepository.findAll();
        return trials.stream()
                .filter(trial -> Objects.equals(trial.getUserId().getId(), loggedUser.getId()))
                .filter(trial -> Objects.equals(trial.getClientId().getId(), clientId))
                .map(TrialResponseDTO::new)
                .collect(Collectors.toList());

    }
}