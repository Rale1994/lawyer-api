package com.laywerapi.laywerapi.services.implementation;

import com.laywerapi.laywerapi.dto.request.TrialAddRequestDTO;
import com.laywerapi.laywerapi.dto.response.TrialAddResponseDTO;
import com.laywerapi.laywerapi.dto.response.TrialResponseDTO;
import com.laywerapi.laywerapi.entity.Trial;
import com.laywerapi.laywerapi.entity.UserRegistrationDetails;
import com.laywerapi.laywerapi.exception.ApiRequestException;
import com.laywerapi.laywerapi.repositories.ClientRepository;
import com.laywerapi.laywerapi.repositories.TrialRepository;
import com.laywerapi.laywerapi.repositories.UserRepository;
import com.laywerapi.laywerapi.shared.Utils;
import com.laywerapi.laywerapi.utils.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrialServiceImplTest {
    @Mock
    private TrialRepository trialRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    Utils utils;
    @InjectMocks
    private TrialServiceImpl trialServiceImpl;


    @Test
    void testAddingTrial() {
        // GIVEN
        var user = TestUtil.createUser();
        var newLogged = new UserRegistrationDetails(user);
        var trialAddRequestDTO = TestUtil.createTrialAddRequestDTO();
        var client = TestUtil.createClientTrial(user);
        Long clientId = 1234L;
        client.setId(clientId);
        Trial trial = new Trial(trialAddRequestDTO, user, client);


        // WHEN
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(userRepository.findById(newLogged.getId())).thenReturn(Optional.of(user));

        // ACTION
        TrialAddResponseDTO actual = trialServiceImpl.addTrial(newLogged, trialAddRequestDTO, clientId);


        assertEquals(actual.getClient().getEmail(), client.getEmail());
    }

    @Test
    void testAddTrialForClientWhoDoesNotExist() {
        // GIVEN
        var user = TestUtil.createUser();
        var newLogged = new UserRegistrationDetails(user);
        var trialAddRequestDTO = TestUtil.createTrialAddRequestDTO();
        var client = TestUtil.createClientTrial(user);
        Long clientId = 1234L;
        client.setId(clientId);

        // WHEN
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // ASSERT
        assertThrows(ApiRequestException.class, () -> trialServiceImpl.addTrial(newLogged, trialAddRequestDTO, clientId));
    }

    @Test
    void testGettingAllClientTrials() {
        // GIVEN
        var user = TestUtil.createUser();
        var newLogged = new UserRegistrationDetails(user);
        Long clientId = 1234L;
        var client = TestUtil.createClientTrial(user);
        client.setId(clientId);
        List<Trial> trials = TestUtil.createTrialList(user, client);

        // WHEN
        when(trialRepository.findAll()).thenReturn(trials);

        // ACTION
        List<TrialResponseDTO> actual = (List<TrialResponseDTO>) trialServiceImpl.getAllClientTrials(newLogged, clientId);

        // ASSERT
        assertNotNull(actual);
    }

    @Test
    void testGettingUsersTrials(){
        // GIVEN
        var user = TestUtil.createUser();
        var newLogged = new UserRegistrationDetails(user);
        var client = TestUtil.createClientTrial(user);
        List<Trial>trials= TestUtil.createTrailsListHourLater(user,client);

        // WHEN
        when(trialRepository.findAll()).thenReturn(trials);

        // ACTION
        List<Trial> actual= trialServiceImpl.findUserTrials();

        // ASSERT
        assertNotNull(actual);
    }

}