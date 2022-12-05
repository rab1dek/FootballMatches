package com.example.FootballMatches.unitTests;

import com.example.FootballMatches.entities.Match;
import com.example.FootballMatches.utils.MatchUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchUnitTest {
    Match match = new Match();

    @BeforeEach
    void init(){
        match.setLeague("LaLiga");
        match.setMatchDate(new Date(2022, 11, 30));
        match.setMatchId(1L);
        match.setMatchTime("20:00");
    }
    @Test
    public void createNewMatch(){
        Match newMatch = new Match();
        newMatch.setLeague("World Cup");
        MatchUtils.validateMatch(newMatch, match);
        assertAll("Test", () -> assertEquals("World Cup", match.getLeague()));
    }
}
