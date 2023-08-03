package com.laywerapi.laywerapi.services.implementation;

import com.laywerapi.laywerapi.dto.response.ClientResponseDTO;
import com.laywerapi.laywerapi.entity.Client;
import com.laywerapi.laywerapi.entity.UserRegistrationDetails;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    Utils utils;
    @InjectMocks
    private ClientServiceImpl clientServiceImp;


    @Test
    void testAddingClient() throws Exception {
        // GIVEN
        var user = TestUtil.createUser();
//        var loggedUser = new CustomUserDetails(user);
        var newLogged=new UserRegistrationDetails(user);
        var clientRequestDTO = TestUtil.createClientRequestDTO();
        var client = new Client(clientRequestDTO, user);

        // WHEN
        when(clientRepository.findByEmail(clientRequestDTO.getEmail())).thenReturn(List.of());
        when(userRepository.findById(newLogged.getId())).thenReturn(Optional.of(user));
        when(clientRepository.save(client)).thenReturn(client);

        // ACTION
        ClientResponseDTO actual = clientServiceImp.addClient(newLogged, clientRequestDTO);

        // ASSERT
        assertEquals(clientRequestDTO.getEmail(), actual.getEmail());
    }

    @Test
    void testTryToAddClientWhoAlreadyExist() {
        // GIVEN
        var user = TestUtil.createUser();
       // var loggedUser = new CustomUserDetails(user);
        var newLogged=new UserRegistrationDetails(user);
        var clientRequestDTO = TestUtil.createClientRequestDTO();
        var client = TestUtil.createClient(clientRequestDTO, user);
        var clientList = TestUtil.creatSameClientListNew(client, newLogged);

        // WHEN
        when(clientRepository.findByEmail(clientRequestDTO.getEmail())).thenReturn(clientList);

        // THEN
        assertThrows(ApiRequestException.class, () -> clientServiceImp.addClient(newLogged, clientRequestDTO));
    }

    @Test
    void testGettingAllClients() {
        // GIVEN
        var user = TestUtil.createUser();
//        var loggedUser = new CustomUserDetails(user);
        var newLogged=new UserRegistrationDetails(user);
        var clientRequestDTO = TestUtil.createClientRequestDTO();
        var clientList = TestUtil.creatClientList(clientRequestDTO, user);

        // WHEN
        when(clientRepository.findAll()).thenReturn(clientList);

        // ACTION
        clientServiceImp.allClients(newLogged);

        // ASSERT
        assertNotNull(clientList);
    }

    @Test
    void testGetOneClientByFirstName() {
        // GIVEN
        var user = TestUtil.createUser();
//        var loggedUser = new CustomUserDetails(user);
        var newLogged=new UserRegistrationDetails(user);
        var firstName = "FirstName";
        var clientList = TestUtil.clientsList();
        var clientResponseDTOsList = TestUtil.clientResponseDTOSList(clientList);

        // WHEN
        when(clientRepository.findAll()).thenReturn(clientList);

        // ACTION
        clientResponseDTOsList = clientServiceImp.getOneByFirstName(newLogged, firstName.toUpperCase());

        // ASSERT
        assertNotNull(clientResponseDTOsList);
    }

    @Test
    void testCantGetClientByFirstName() {
        // GIVEN
        var user = TestUtil.createUser();
//        var loggedUser = new CustomUserDetails(user);
        var newLogged=new UserRegistrationDetails(user);
        var firstName = "FirstName";
        var clientList = TestUtil.clientsList();
        var clientResponseDTOsList = TestUtil.clientResponseDTOSList(clientList);

        // WHEN
        when(clientRepository.findAll()).thenReturn(List.of());

        // ASSERT
        assertThrows(ApiRequestException.class, () -> clientServiceImp.getOneByFirstName(newLogged, firstName));
    }

    @Test
    void testUpdateClientInformation() {
        // GIVEN
        var user = TestUtil.createUser();
        //var loggedUser = new CustomUserDetails(user);
        var newLogged=new UserRegistrationDetails(user);
        var clientRequestDTO = TestUtil.createClientRequestDTO();
        var client = new Client(clientRequestDTO, user);
        var updatedClient = TestUtil.createUpdatedClient(clientRequestDTO);
        Long clientId = 123456L;

        // WHEN
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(utils.checkingForUpdates(client, clientRequestDTO)).thenReturn(updatedClient);
        when(clientRepository.save(updatedClient)).thenReturn(updatedClient);

        // ACTION
        ClientResponseDTO actual = clientServiceImp.updateClient(newLogged, clientRequestDTO, clientId);

        // ASSERT
        assertNotNull(actual);
        assertEquals(clientRequestDTO.getFirstName(), updatedClient.getFirstName());
    }

    @Test
    void testTryToUpdateClientWhoDoesNotExist() {
        // GIVEN
        var user = TestUtil.createUser();
//        var loggedUser = new CustomUserDetails(user);
        var newLogged=new UserRegistrationDetails(user);
        var clientRequestDTO = TestUtil.createClientRequestDTO();
        var client = new Client(clientRequestDTO, user);
        var updatedClient = TestUtil.createUpdatedClient(clientRequestDTO);
        Long clientId = 123456L;

        // WHEN
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // ASSERT
        assertThrows(ApiRequestException.class, () -> clientServiceImp.updateClient(newLogged, clientRequestDTO, clientId));
    }

    @Test
    void testTryToUpdateClientWhoDoesNotExistForCurrentlyLawyer() {
        // GIVEN
        var user = TestUtil.createUser();
        user.setId(1111L);
        //var loggedUser = new CustomUserDetails(user);
        var newLogged=new UserRegistrationDetails(user);
        var clientRequestDTO = TestUtil.createClientRequestDTO();
        var client = new Client(clientRequestDTO, user);
        var otherClient=TestUtil.createDifferentClient();
        Long clientId = 123456L;

        // WHEN
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(otherClient));

        // ASSERT
        assertThrows(ApiRequestException.class, () -> clientServiceImp.updateClient(newLogged, clientRequestDTO, clientId));
    }

    @Test
    void testDeleteClient(){
        // GIVEN
        var user = TestUtil.createUser();
        //var loggedUser = new CustomUserDetails(user);
        var newLogged=new UserRegistrationDetails(user);
        var clientRequestDTO = TestUtil.createClientRequestDTO();
        var clientList = TestUtil.creatClientList(clientRequestDTO, user);
        Long clientId = 123456L;

        // WHEN
        when(clientRepository.findAll()).thenReturn(clientList);

        // THEN
        clientServiceImp.deleteClient(newLogged,clientId);



    }
}