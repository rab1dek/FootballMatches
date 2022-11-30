package com.example.FootballMatches.controllers;

import com.example.FootballMatches.entities.Match;
import com.example.FootballMatches.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/app-api/matches")
public class MatchController {
    @Autowired
    MatchService matchService;

    @GetMapping
    public List<Match> getMatches() {
        return matchService.getAll();
    }
    @GetMapping("/{id")
    public Match getMatchById(@PathVariable("id") Long id) {
        return matchService.findByMatchId(id);
    }
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Match create(@RequestBody Match match) {
        return matchService.save(match);
    }
    @PutMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Match update(@RequestBody Match match) {
        return matchService.save(match);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        try {
            matchService.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {

        }
    }
}
