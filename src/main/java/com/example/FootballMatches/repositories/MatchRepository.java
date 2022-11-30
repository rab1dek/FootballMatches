package com.example.FootballMatches.repositories;

import com.example.FootballMatches.entities.Match;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MatchRepository extends CrudRepository<Match, Long> {
    @Override
    List<Match> findAll();
}
