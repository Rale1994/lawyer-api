package com.laywerapi.laywerapi.repositories;

import com.laywerapi.laywerapi.entity.Client;
import com.laywerapi.laywerapi.entity.Trial;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClientRepository extends  CrudRepository<Client, Long>, RevisionRepository<Client, Long, Integer> {
    List<Client> findByEmail(String email);

    List<Client> findByUserId(Long userId);

    Client findByFirstName(String clientName);

}
