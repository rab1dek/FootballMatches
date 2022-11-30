package com.example.FootballMatches.services;

import com.example.FootballMatches.entities.Referee;
import com.example.FootballMatches.repositories.RefereeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefereeService {
    @Autowired
    RefereeRepository refRep;

    public Referee save(Referee ref) {
        return refRep.save(ref);
    }
    public List<Referee> getAll() {
        return refRep.findAll();
    }
    public Referee findByRefId(Long id) {
        return refRep.findById(id).orElse(null);
    }
    public void delete(Referee ref) {
        refRep.delete(ref);
    }
    public void deleteById(Long id) {
        refRep.deleteById(id);
    }
}
