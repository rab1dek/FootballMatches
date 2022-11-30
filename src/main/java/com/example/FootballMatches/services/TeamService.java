package com.example.FootballMatches.services;

import com.example.FootballMatches.entities.Team;
import com.example.FootballMatches.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    @Autowired
    TeamRepository teamRep;

    public Team save(Team team) {
        return teamRep.save(team);
    }
    public List<Team> getAll() {
        return teamRep.findAll();
    }
    public Team findByTeamid(Long id) {
        return teamRep.findById(id).orElse(null);
    }
    public void delete(Team team) {
        teamRep.delete(team);
    }
    public void deleteById(Long id){
        teamRep.deleteById(id);
    }
}
