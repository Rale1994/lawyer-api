package com.laywerapi.laywerapi.resource;


import com.laywerapi.laywerapi.dto.request.ClientRequestDTO;
import com.laywerapi.laywerapi.dto.response.ClientResponseDTO;
import com.laywerapi.laywerapi.entity.CustomUserDetails;
import com.laywerapi.laywerapi.entity.UserRegistrationDetails;
import com.laywerapi.laywerapi.services.ClientService;
import com.laywerapi.laywerapi.services.implementation.UserRegistrationDetailsImpl;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/clients")
@Api
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


//    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    public ClientResponseDTO addClient(@AuthenticationPrincipal UserRegistrationDetails loggedUser, @RequestBody ClientRequestDTO clientRequestDTO) throws Exception {
        return clientService.addClient(loggedUser, clientRequestDTO);
    }

    //    @PreAuthorize("hasRole('USER')")
    @GetMapping("/all")
    public List<ClientResponseDTO> allClients(@AuthenticationPrincipal UserRegistrationDetails loggedUser) {
        return clientService.allClients(loggedUser);
    }

    //    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{clientName}")
    public List<ClientResponseDTO> getOneByFirstName(@AuthenticationPrincipal UserRegistrationDetails loggedUser, @PathVariable String clientName) {
        return clientService.getOneByFirstName(loggedUser, clientName);
    }


    @PutMapping("/update/{clientId}")
    public ClientResponseDTO updateClient(@AuthenticationPrincipal UserRegistrationDetails loggedUser, @RequestBody ClientRequestDTO clientRequestDTO, @PathVariable Long clientId) {
        return clientService.updateClient(loggedUser, clientRequestDTO, clientId);
    }

    //    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{clientId}")
    public ResponseEntity<?> deleteClient(@AuthenticationPrincipal UserRegistrationDetails loggedUser, @PathVariable Long clientId) {
        clientService.deleteClient(loggedUser, clientId);
        return ResponseEntity.ok().build();
    }
}
