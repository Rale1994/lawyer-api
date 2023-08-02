package com.laywerapi.laywerapi.repositories;
import com.laywerapi.laywerapi.entity.Trial;
import com.laywerapi.laywerapi.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, RevisionRepository<User, Long, Integer> {
    Optional<User> findByEmail(String username);

    Optional<User> findByUsername(String username);
}
