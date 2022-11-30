package com.example.FootballMatches.repositories;

import com.example.FootballMatches.entities.Referee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefereeRepository extends CrudRepository<Referee, Long> {
    @Override
    List<Referee> findAll();
}
