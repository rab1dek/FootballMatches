package com.example.FootballMatches.services;

import com.example.FootballMatches.entities.Match;
import com.example.FootballMatches.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {
    @Autowired
    MatchRepository matchRep;

    public Match save(Match match) {
        return matchRep.save(match);
    }
    public List<Match> getAll() {
        return matchRep.findAll();
    }
    public Match findByMatchId(Long id) {
        return matchRep.findById(id).orElse(null);
    }
    public void delete(Match match) {
        matchRep.delete(match);
    }
    public void deleteById(Long id) {
        matchRep.deleteById(id);
    }
}
