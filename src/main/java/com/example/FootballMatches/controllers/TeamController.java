package com.example.FootballMatches.controllers;

import com.example.FootballMatches.entities.Team;
import com.example.FootballMatches.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/app-api/teams")
public class TeamController {
    @Autowired
    TeamService teamService;

    @GetMapping
    public List<Team> getTeams() {
        return teamService.getAll();
    }
    @GetMapping("/{id")
    public Team getTeamById(@PathVariable("id") Long id) {
        return teamService.findByTeamid(id);
    }
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Team create(@RequestBody Team team) {
        return teamService.save(team);
    }
    @PutMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Team update(@RequestBody Team team) {
        return teamService.save(team);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        try {
            teamService.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {

        }
    }
}


