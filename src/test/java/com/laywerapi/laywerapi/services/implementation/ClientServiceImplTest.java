package com.laywerapi.laywerapi.services.implementation;

import com.laywerapi.laywerapi.dto.response.ClientResponseDTO;
import com.laywerapi.laywerapi.entity.Client;
import com.laywerapi.laywerapi.entity.CustomUserDetails;
import com.laywerapi.laywerapi.exception.ApiRequestException;
import com.laywerapi.laywerapi.repositories.ClientRepository;
import com.laywerapi.laywerapi.repositories.UserRepository;
import com.laywerapi.laywerapi.shared.Utils;
import com.laywerapi.laywerapi.utils.TestUtil;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Utils utils;
    @InjectMocks
    private ClientServiceImpl clientServiceImp;


    @Test
    void testAddingClient() throws Exception {
        // GIVEN
        var user = TestUtil.createUser();
        var loggedUser = new CustomUserDetails(user);
        var clientRequestDTO = TestUtil.createClientRequestDTO();
        var client = TestUtil.createClient(clientRequestDTO, user);

        // WHEN
        when(clientRepository.findByEmail(clientRequestDTO.getEmail())).thenReturn(List.of());
        when(userRepository.findById(loggedUser.getId())).thenReturn(Optional.of(user));

        // ACTION
        clientRepository.save(client);
        clientServiceImp.addClient(loggedUser, clientRequestDTO);

        // ASSERT
        assertEquals(clientRequestDTO.getEmail(), client.getEmail());
    }

    @Test
    void testTryToAddClientWhoAlreadyExist() {
        // GIVEN
        var user = TestUtil.createUser();
        var loggedUser = new CustomUserDetails(user);
        var clientRequestDTO = TestUtil.createClientRequestDTO();
        var client = TestUtil.createClient(clientRequestDTO, user);
        var clientList = TestUtil.creatClientList(clientRequestDTO, user);

        // WHEN
        when(clientRepository.findByEmail(clientRequestDTO.getEmail())).thenReturn(clientList);

        // THEN
        assertThrows(ApiRequestException.class, () -> clientServiceImp.addClient(loggedUser, clientRequestDTO));
    }

    @Test
    void testGettingAllClients() {
        // GIVEN
        var user = TestUtil.createUser();
        var loggedUser = new CustomUserDetails(user);
        var clientRequestDTO = TestUtil.createClientRequestDTO();
        var clientList = TestUtil.creatClientList(clientRequestDTO, user);

        // WHEN
        when(clientRepository.findAll()).thenReturn(clientList);

        // ACTION
        clientServiceImp.allClients(loggedUser);

        // ASSERT
        assertNotNull(clientList);
    }

    @Test
    void testGetOneClientByFirstName() {
        // GIVEN
        var user = TestUtil.createUser();
        var loggedUser = new CustomUserDetails(user);
        var firstName = "FirstName";
        var clientList = TestUtil.clientsList();
        var clientResponseDTOsList=TestUtil.clientResponseDTOSList(clientList);

        // WHEN
        when(clientRepository.findAll()).thenReturn(clientList);

        // ACTION
        clientResponseDTOsList = clientServiceImp.getOneByFirstName(loggedUser, firstName.toUpperCase());

        // ASSERT
        assertNotNull(clientResponseDTOsList);
    }

    @Test
    void testCantGetClientByFirstName() {
        // GIVEN
        var user = TestUtil.createUser();
        var loggedUser = new CustomUserDetails(user);
        var firstName = "FirstName";
        var clientList = TestUtil.clientsList();
        var clientResponseDTOsList=TestUtil.clientResponseDTOSList(clientList);

        // WHEN
        when(clientRepository.findAll()).thenReturn(List.of());

        // ASSERT
        assertThrows(ApiRequestException.class, () -> clientServiceImp.getOneByFirstName(loggedUser, firstName));
    }
}