package com.example.FootballMatches.utils;

import com.example.FootballMatches.entities.Match;

public class MatchUtils {
    public static Match validateMatch(Match match, Match m){
        if(match.getMatchDate() != null){
            m.setMatchDate(match.getMatchDate());
        }
        if(match.getMatchTime() != null){
            m.setMatchTime(match.getMatchTime());
        }
        if(match.getLeague() != null){
            m.setLeague(match.getLeague());
        }
        return m;
    }
}
