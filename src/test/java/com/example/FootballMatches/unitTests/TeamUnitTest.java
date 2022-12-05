package com.example.FootballMatches.unitTests;

import com.example.FootballMatches.entities.Team;
import com.example.FootballMatches.utils.TeamUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeamUnitTest {
    Team team = new Team();

    @BeforeEach
    void init() {
        team.setTeamId(1L);
        team.setName("FC Barcelona");
        team.setLeague("LaLiga");
        team.setCoach("Xavi");
        List<String> footballers = Arrays.asList("Lewandowski", "Dembele", "Depay");
        team.setFootballers(footballers);
    }
    @Test
    void createNewTeam(){
        Team newTeam = new Team();
        newTeam.setTeamId(1L);
        newTeam.setName("Legia Warszawa");
        newTeam.setLeague("Ekstraklasa");

        TeamUtils.validateTeam(newTeam, team);
        assertAll("Test",
                () -> assertEquals("Legia Warszawa", team.getName()),
                () -> assertEquals("Ekstraklasa", team.getLeague())
        );
    }
}
