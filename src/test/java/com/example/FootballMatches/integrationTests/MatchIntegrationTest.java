package com.example.FootballMatches.integrationTests;

import com.example.FootballMatches.FootballMatchesApplication;
import com.example.FootballMatches.entities.Match;
import com.example.FootballMatches.entities.Referee;
import com.example.FootballMatches.entities.Team;
import com.example.FootballMatches.repositories.MatchRepository;
import com.example.FootballMatches.repositories.RefereeRepository;
import com.example.FootballMatches.repositories.TeamRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = FootballMatchesApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@TestPropertySource(locations = "classpath:applicationtests.properties")
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MatchIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    MatchRepository matchRepository;
    @Autowired
    RefereeRepository refereeRepository;
    @Autowired
    TeamRepository teamRepository;

    ObjectMapper objectMapper = new ObjectMapper();
    public Match createMatchForTest() {
        Match match = new Match();
        match.setMatchId(1L);
        match.setMatchDate(new Date(2022, 30, 11));
        match.setLeague("Friendly");
        match.setMatchTime("20:00");

        Referee ref = new Referee();
        ref.setRefereeId(1L);
        ref.setName("Javier");
        ref.setSurname("Hernandez Hernandez");
        ref.setLeague("LaLiga");
        createReferee(ref);

        match.setReferee(ref);

        Team team = new Team();
        team.setTeamId(1L);
        team.setName("FC Barcelona");
        team.setLeague("LaLiga");
        team.setCoach("Xavi");
        List<String> footballers = Arrays.asList("Lewandowski", "Dembele", "Depay");
        team.setFootballers(footballers);
        createTeam(team);

        Team team2 = new Team();
        team2.setTeamId(2L);
        team2.setName("Juventus");
        team2.setLeague("Serie A");
        team2.setCoach("Allegri");
        List<String> footballers2 = Arrays.asList("Milik", "Vlahovic", "Szczesny");
        team2.setFootballers(footballers2);
        createTeam(team2);

        match.setTeam(team);
        match.setTeam(team2);
        return match;
    }
    @Test
    public void createMatchTest() throws Exception {
        Match match = new Match();
        match.setMatchId(1L);
        match.setMatchDate(new Date(2022, 30, 11));
        match.setLeague("LaLiga");
        match.setMatchTime("20:00");
        createMatch(match);

        mvc.perform(get("/app-api/matches/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.league", is("LaLiga")));
    }
    @Test
    public void createMatchWithTeamsAndRefsTest() throws Exception {
        Match match = createMatchForTest();

        mvc.perform(MockMvcRequestBuilders.post("/app-api/matches/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(match)));
        List<Match> foundMatches = matchRepository.findAll();
        assertEquals(foundMatches.get(0).getLeague(), "Friendly");
        assertEquals(foundMatches.get(0).getReferee().getName(), "Javier");
        assertEquals(foundMatches.get(0).getTeam().getName(), "FC Barcelona");
    }
    @Test
    public void updateMatchTest() throws Exception {
        Match match = createMatchForTest();

        createMatch(match);

        match.setLeague("Friendly");

        mvc.perform(MockMvcRequestBuilders.put("/app-api/matches/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(match)));

        List<Match> foundMatches = matchRepository.findAll();

        assertEquals(foundMatches.get(0).getLeague(), "Friendly");
    }
    @Test
    public void deleteMatch() throws Exception {
        Match match = createMatchForTest();

        createMatch(match);

        mvc.perform(MockMvcRequestBuilders.delete("/app-api/matches/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(match)));

        Optional<Match> foundMatches = matchRepository.findById(1L);
        assertTrue(foundMatches.isEmpty());
    }
    private void createMatch(Match m) {
        matchRepository.save(m);
    }
    private void createReferee(Referee r) {
        refereeRepository.save(r);
    }
    private void createTeam(Team t) {
        teamRepository.save(t);
    }
}
