package com.laywerapi.laywerapi.resource;


import com.laywerapi.laywerapi.dto.request.ClientRequestDTO;
import com.laywerapi.laywerapi.dto.response.ClientResponseDTO;
import com.laywerapi.laywerapi.entity.CustomUserDetails;
import com.laywerapi.laywerapi.services.ClientService;
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


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/add")
    public void addClient(@AuthenticationPrincipal CustomUserDetails loggedUser, @RequestBody ClientRequestDTO clientRequestDTO) throws Exception {
        clientService.addClient(loggedUser, clientRequestDTO);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public List<ClientResponseDTO> allClients(@AuthenticationPrincipal CustomUserDetails loggedUser) {
        return clientService.allClients(loggedUser);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{clientName}")
    public List<ClientResponseDTO> getOneByFirstName(@AuthenticationPrincipal CustomUserDetails loggedUser, @PathVariable String clientName) {
        return clientService.getOneByFirstName(loggedUser, clientName);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update/{clientId}")
    public ClientResponseDTO updateClient(@AuthenticationPrincipal CustomUserDetails loggedUser, @RequestBody ClientRequestDTO clientRequestDTO, @PathVariable Long clientId) {
        return clientService.updateClient(loggedUser, clientRequestDTO, clientId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{clientId}")
    public ResponseEntity<?> deleteClient(@AuthenticationPrincipal CustomUserDetails loggedUser, @PathVariable Long clientId) {
        clientService.deleteClient(loggedUser, clientId);
        return ResponseEntity.ok().build();
    }
}
