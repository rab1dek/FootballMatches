package com.example.FootballMatches.controllers;

import com.example.FootballMatches.entities.Referee;
import com.example.FootballMatches.services.RefereeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app-api/referees")
public class RefereeController {
    @Autowired
    RefereeService refService;

    @GetMapping
    public List<Referee> getReferees() {
        return refService.getAll();
    }
    @GetMapping("/{id}")
    public Referee getRefereeById(@PathVariable("id") Long id) {
        return refService.findByRefId(id);
    }
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Referee create(@RequestBody Referee referee) {
        return refService.save(referee);
    }
    @PutMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Referee update(@RequestBody Referee referee) {
        return refService.save(referee);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        try {
            refService.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {

        }
    }
}
