package com.example.FootballMatches.unitTests;

import com.example.FootballMatches.entities.Match;
import com.example.FootballMatches.utils.MatchUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class MatchUnitTest {
    Match match = new Match();

    @BeforeEach
    void init(){
        match.setLeague("LaLiga");
        match.setMatchDate(new Date(2022, Calendar.DECEMBER, 30));
        match.setMatchId(1L);
        match.setMatchTime("20:00");
    }
    @Test
    public void createNewMatchWithGivenLeague(){
        Match newMatch = new Match();
        newMatch.setLeague("World Cup");
        MatchUtils.validateMatch(newMatch, match);
        assertAll("League Test", () ->
                assertEquals("World Cup", match.getLeague())
        );
    }
    @Test
    public void createNewMatchWithGivenDate(){
        Match newMatch = new Match();
        newMatch.setMatchDate(new Date(2022, 2, 10));
        MatchUtils.validateMatch(newMatch, match);
        assertAll("Date Test", () ->
                assertEquals(new Date(2022, 2, 10), match.getMatchDate())
        );
    }

    @Test
    public void createNewMatchWithNotValidInput(){
        Match patchVisit = new Match();
        patchVisit.setLeague(null);
        Match visit = new Match();

        MatchUtils.validateMatch(patchVisit, visit);
        assertNull(visit.getLeague());
    }

    @Test
    public void changeGivenMatchData() {
        Match newMatch = new Match();
        newMatch.setLeague("World Cup");
        newMatch.setMatchDate(new Date(2022, 2, 10));
        newMatch.setMatchTime("21:00");
        Match match = new Match();

        MatchUtils.validateMatch(newMatch, match);

        assertAll("Transaction quota",
                () ->  assertEquals(match.getLeague(),"World Cup"),
                () -> assertEquals(match.getMatchDate(),new Date(2022, 2, 10)),
                () ->  assertEquals(match.getMatchTime(),"21:00")
        );
    }




}
