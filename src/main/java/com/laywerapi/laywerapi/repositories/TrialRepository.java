package com.laywerapi.laywerapi.repositories;

import com.laywerapi.laywerapi.entity.Trial;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrialRepository extends CrudRepository<Trial, Long> {
    List<Trial> findAllById(Long clientId);
}
