package com.example.FootballMatches.repositories;

import com.example.FootballMatches.entities.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
    @Override
    List<Team> findAll();
}
