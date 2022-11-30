package com.example.FootballMatches.integrationTests;

import com.example.FootballMatches.FootballMatchesApplication;
import com.example.FootballMatches.entities.Team;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = FootballMatchesApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@TestPropertySource(locations = "classpath:applicationtests.properties")
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TeamIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private TeamRepository teamRepository;

    ObjectMapper objectMapper = new ObjectMapper();
    public Team createTeam() {
        Team team = new Team();
        team.setTeamId(1L);
        team.setName("FC Barcelona");
        team.setLeague("LaLiga");
        team.setCoach("Xavi");
        List<String> footballers = Arrays.asList("Lewandowski", "Dembele", "Depay");
        team.setFootballers(footballers);
        return team;
    }
    @Test
    public void createTeamAndFindId() throws Exception {
        Team team = createTeam();
        mvc.perform(post("/app-api/teams").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(team)));
        Optional<Team> foundTeam = teamRepository.findById(1L);
        assertEquals(foundTeam.get().getName(), "FC Barcelona");
    }
    @Test
    public void updateTeam() throws Exception {
        Team team = createTeam();
        mvc.perform(post("/app-api/teams").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(team)));
        team.setCoach("Michniewicz");
        team.setLeague("World Cup");
        List<String> PLfootballers = Arrays.asList("Lewandowski", "Szczesny", "Beres");
        team.setFootballers(PLfootballers);
        team.setName("Poland");
        mvc.perform(post("/app-api/teams").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(team)));
        Optional<Team> foundTeam = teamRepository.findById(1L);
        assertEquals(foundTeam.get().getName(), "Poland");
    }
    @Test
    public void getTeams() throws Exception {
        Team team2 = new Team();
        Team team = createTeam();
        mvc.perform(post("/app-api/teams").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(team)));
        team2.setTeamId(2L);
        team2.setName("Juventus");
        team2.setLeague("Serie A");
        team2.setCoach("Allegri");
        List<String> footballers2 = Arrays.asList("Milik", "Vlahovic", "Szczesny");
        team2.setFootballers(footballers2);
        mvc.perform(post("/app-api/teams").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(team2)));
        mvc.perform(get("/app-api/teams").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].name", is("FC Barcelona")))
                .andExpect(jsonPath("$[1].name", is("Juventus")));
    }
    @Test
    public void getTeamById() throws Exception {
        Team team = createTeam();
        mvc.perform(post("/app-api/teams").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(team)));
        mvc.perform(get("/app-api/teams/{id}", team.getTeamId()).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is("FC Barcelona")));
    }
    @Test
    public void deleteTeam() throws Exception {
        Team team = createTeam();
        mvc.perform(post("/app-api/teams").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(team)));
        Optional<Team> foundTeam = teamRepository.findById(1L);
        mvc.perform(delete("/app-api/teams/{id}", foundTeam.get().getTeamId()));
        Optional<Team> foundTeam1 = teamRepository.findById(1L);
        assertTrue(foundTeam1.isEmpty());
    }
}
