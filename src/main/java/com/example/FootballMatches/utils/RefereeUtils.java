package com.example.FootballMatches.utils;

import com.example.FootballMatches.entities.Referee;

public class RefereeUtils {
    public static Referee validateRef(Referee referee, Referee ref) {
        if(referee.getLeague() != null) {
            ref.setLeague(referee.getLeague());
        }
        if(referee.getName() != null) {
            ref.setName(referee.getName());
        }
        if(referee.getSurname() != null) {
            ref.setSurname(referee.getSurname());
        }
        return ref;
    }
}
