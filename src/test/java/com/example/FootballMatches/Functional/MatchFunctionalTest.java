package com.example.FootballMatches.Functional;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = FootballMatchesApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@TestPropertySource(locations = "classpath:applicationtests.properties")
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MatchFunctionalTest {
    @Autowired
    private MockMvc mvc;
    JSONObject testMatch = new JSONObject();
    JSONObject testMatchUpdated = new JSONObject();

    public void matchInit(JSONObject match) throws Exception{
        match.put("matchId", 1);
        //match.put("matchDate", new Date(2022, 1, 2));
        match.put("matchDate", "2022-01-02");
        match.put("matchTime", "14:30");
        match.put("league", "Premiere League");
    }
    public void matchUpdate(JSONObject match) throws Exception{
        match.put("matchId", 1);
        //match.put("matchDate", new Date(2022, 5, 2));
        match.put("matchDate", "2022-05-02");
        match.put("matchTime", "15:30");
        match.put("league", "LaLiga");
    }



    @Test
    public void getMatchesIfEmpty() throws Exception {
        mvc.perform(get("/app-api/matches")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    public void getMatchById() throws Exception {
        matchInit(testMatch);

        mvc.perform(MockMvcRequestBuilders.post("/app-api/matches/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testMatch.toString()));

        mvc.perform(get("/app-api/matches/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.league", is("Premiere League")));
    }

    @Test
    public void updateMatchWithValidInput() throws Exception {
        matchInit(testMatch);

        mvc.perform(MockMvcRequestBuilders.post("/app-api/matches/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testMatch.toString()));

        mvc.perform(get("/app-api/matches/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.matchTime", is("14:30")))
                .andExpect(jsonPath("$.league", is("Premiere League")));

        matchUpdate(testMatchUpdated);

        mvc.perform(MockMvcRequestBuilders.patch("/app-api/matches/1/1/1") //not working
                .contentType(MediaType.APPLICATION_JSON)
                .content(testMatchUpdated.toString()));

        mvc.perform(get("/app-api/matches/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.matchTime", is("15:30")))
                .andExpect(jsonPath("$.league", is("LaLiga")));
    }

    @Test
    public void deleteMatchWithValidInput() throws Exception {
        matchInit(testMatch);

        mvc.perform(MockMvcRequestBuilders.post("/app-api/matches/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testMatch.toString()));

        mvc.perform(get("/app-api/matches/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.matchTime", is("14:30")))
                .andExpect(jsonPath("$.league", is("Premiere League")));

        mvc.perform(MockMvcRequestBuilders.delete("/app-api/matches/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testMatch.toString()));

        mvc.perform(get("/app-api/matches")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

}
