package com.example.FootballMatches.functionalTests;

import com.example.FootballMatches.FootballMatchesApplication;
import org.json.JSONObject;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = FootballMatchesApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@TestPropertySource(locations = "classpath:applicationtests.properties")
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TeamFunctionalTest {
    @Autowired
    private MockMvc mvc;

    JSONObject team1 = new JSONObject();
    JSONObject team2 = new JSONObject();
    JSONObject updatedTeam = new JSONObject();

    public void team1Init(JSONObject team) throws Exception{
        team.put("teamId", 1);
        team.put("name", "FC Barcelona");
        team.put("league", "LaLiga");
        team.put("coach", "Xavi");
    }
    public void team2Init(JSONObject team) throws Exception{
        team.put("teamId", 2);
        team.put("name", "Legia Warszawa");
        team.put("league", "Ekstraklasa");
        team.put("coach", "Ktos tam");
    }

    public void teamUpdate(JSONObject team) throws Exception{
        team.put("teamId", 2);
        team.put("name", "Rakow Czestochowa");
        team.put("league", "Liga Konferencji");
        team.put("coach", "Marek Papszun");
    }
    @Test
    public void whenValidInput_CreateTeamTest() throws Exception {
        team1Init(team1);
        mvc.perform(post("/app-api/teams").contentType(MediaType.APPLICATION_JSON).content(team1.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(team1.get("name"))))
                .andExpect(jsonPath("$.coach", is(team1.get("coach"))))
                .andExpect(jsonPath("$.league", is(team1.get("league"))));
    }
    @Test
    public void whenValidInput_thenUpdateTeamTest() throws Exception {
        team1Init(team1);
        teamUpdate(updatedTeam);

        mvc.perform(post("/app-api/teams").contentType(MediaType.APPLICATION_JSON)
                .content(team1.toString()));
        mvc.perform(get("/app-api/teams/{id}", team1.get("teamId"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(team1.toString()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(team1.get("name"))))
                .andExpect(jsonPath("$.coach", is(team1.get("coach"))))
                .andExpect(jsonPath("$.league", is(team1.get("league"))));
    }
    @Test
    public void whenValidInput_thenDeleteTeamTest() throws Exception {
        team1Init(team1);
        mvc.perform(post("/app-api/teams").contentType(MediaType.APPLICATION_JSON)
                .content(team1.toString()));
        mvc.perform(delete("/app-api/teams/{id}", team1.get("teamId"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
    @Test
    public void givenTeam_whenLoadTeams_thenStatus200() throws Exception {
        team1Init(team1);
        team2Init(team2);
        mvc.perform(post("/app-api/teams").contentType(MediaType.APPLICATION_JSON)
                .content(team1.toString()));
        mvc.perform(post("/app-api/teams").contentType(MediaType.APPLICATION_JSON)
                .content(team2.toString()));
        mvc.perform(get("/app-api/teams").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].name", is("FC Barcelona")))
                .andExpect(jsonPath("$[1].name", is("Legia Warszawa")))
                .andExpect(jsonPath("$[0].coach", is("Xavi")))
                .andExpect(jsonPath("$[1].coach", is("Ktos tam")));
    }
    @Test
    public void givenTeam_whenLoadTeamById_thenStatus200() throws Exception {
        team1Init(team1);
        mvc.perform(post("/app-api/teams").contentType(MediaType.APPLICATION_JSON)
                .content(team1.toString()));
        mvc.perform(get("/app-api/teams/{id}", team1.get("teamId"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("FC Barcelona")))
                .andExpect(jsonPath("$.coach", is("Xavi")));
    }
    @Test
    public void whenValidInput_thenUpdateTeamAndReturn() throws Exception {
        team1Init(team1);
        teamUpdate(updatedTeam);
        mvc.perform(post("/app-api/teams").contentType(MediaType.APPLICATION_JSON)
                .content(team1.toString()));
        mvc.perform(put("/app-api/teams").contentType(MediaType.APPLICATION_JSON)
                        .content(updatedTeam.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Rakow Czestochowa")))
                .andExpect(jsonPath("$.coach", is("Marek Papszun")))
                .andExpect(jsonPath("$.league", is("Liga Konferencji")));
    }
}
