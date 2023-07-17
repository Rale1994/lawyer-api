package com.laywerapi.laywerapi.repositories;
import com.laywerapi.laywerapi.entity.UserT;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserT, Long> {
    Optional<UserT> findByEmail(String username);

    Optional<UserT> findByUsername(String username);
}
