package com.example.FootballMatches.unitTests;

import com.example.FootballMatches.entities.Referee;
import com.example.FootballMatches.utils.RefereeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RefereeUnitTest {
    Referee referee = new Referee();

    @BeforeEach
    void init() {
        referee.setRefereeId(1L);
        referee.setName("Szymon");
        referee.setSurname("Marciniak");
        referee.setLeague("Ekstraklasa");
    }
    @Test
    void createNewReferee(){
        Referee newRef = new Referee();
        newRef.setRefereeId(1L);
        newRef.setName("Tomasz");
        newRef.setSurname("Musial");
        newRef.setLeague("Serie A");

        RefereeUtils.validateRef(newRef, referee);

        assertAll("Test", () -> assertEquals("Tomasz", referee.getName()),
                () -> assertEquals("Musial", referee.getSurname()));
    }
}
