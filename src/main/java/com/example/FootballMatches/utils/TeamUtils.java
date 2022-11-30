package com.example.FootballMatches.utils;

import com.example.FootballMatches.entities.Team;

public class TeamUtils {
    public static Team validateTeam(Team team, Team t){
        if(team.getName() != null){
            t.setName(team.getName());
        }
        if(team.getCoach() != null){
            t.setCoach(team.getCoach());
        }
        if(team.getLeague() != null){
            t.setLeague(team.getLeague());
        }
        if(team.getFootballers() != null){
            t.setFootballers(team.getFootballers());
        }
        return t;
    }
}
