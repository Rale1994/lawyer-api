package com.laywerapi.laywerapi.repositories;

import com.laywerapi.laywerapi.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    List<Client> findByEmail(String email);

    List<Client> findByUserId(Long userId);

    Client findByFirstName(String clientName);

}
