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
public class RefereeFunctionalTest {
    @Autowired
    private MockMvc mvc;

    JSONObject referee1 = new JSONObject();
    JSONObject referee2 = new JSONObject();
    JSONObject updatedReferee = new JSONObject();

    public void referee1Init(JSONObject referee) throws Exception{
        referee.put("refereeId", 1);
        referee.put("name", "Emanuel");
        referee.put("surname", "Smith");
        referee.put("league", "Premiere League");
        referee.put("country", "England");
    }
    public void referee2Init(JSONObject referee) throws Exception{
        referee.put("refereeId", 2);
        referee.put("name", "John");
        referee.put("surname", "Willow");
        referee.put("league", "Ekstraklasa");
        referee.put("country", "Germany");
    }

    public void refereeUpdate(JSONObject referee) throws Exception{
        referee.put("refereeId", 2);
        referee.put("name", "Matty");
        referee.put("surname", "Willow");
        referee.put("league", "LaLiga");
        referee.put("country", "Germany");
    }


    @Test
    public void createRefereeWithValidInputTest() throws Exception {
        referee1Init(referee1);
        mvc.perform(post("/app-api/referees").contentType(MediaType.APPLICATION_JSON).content(referee1.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(referee1.get("name"))))
                .andExpect(jsonPath("$.surname", is(referee1.get("surname"))))
                .andExpect(jsonPath("$.league", is(referee1.get("league"))));
    }

    @Test
    public void updateRefereeWithValidInputTest() throws Exception {
        referee1Init(referee1);
        refereeUpdate(updatedReferee);

        mvc.perform(post("/app-api/referees").contentType(MediaType.APPLICATION_JSON)
                .content(referee1.toString()));
        mvc.perform(get("/app-api/referees/{id}", referee1.get("refereeId"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(referee1.toString()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(referee1.get("name"))))
                .andExpect(jsonPath("$.surname", is(referee1.get("surname"))))
                .andExpect(jsonPath("$.league", is(referee1.get("league"))));
    }

    @Test
    public void deleteRefereeWithValidInputTest() throws Exception {
        referee1Init(referee1);
        mvc.perform(post("/app-api/referees").contentType(MediaType.APPLICATION_JSON)
                .content(referee1.toString()));
        mvc.perform(delete("/app-api/referees/{id}", referee1.get("refereeId"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void postReferees_thenLoadReferees_thenStatus200() throws Exception {
        referee1Init(referee1);
        referee2Init(referee2);
        mvc.perform(post("/app-api/referees").contentType(MediaType.APPLICATION_JSON)
                .content(referee1.toString()));
        mvc.perform(post("/app-api/referees").contentType(MediaType.APPLICATION_JSON)
                .content(referee2.toString()));
        mvc.perform(get("/app-api/referees").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].name", is("Emanuel")))
                .andExpect(jsonPath("$[1].name", is("John")))
                .andExpect(jsonPath("$[0].surname", is("Smith")))
                .andExpect(jsonPath("$[1].surname", is("Willow")));
    }

    @Test
    public void postReferee_thenLoadRefereeById_thenStatus200() throws Exception {
        referee1Init(referee1);
        mvc.perform(post("/app-api/referees").contentType(MediaType.APPLICATION_JSON)
                .content(referee1.toString()));
        mvc.perform(get("/app-api/referees/{id}", referee1.get("refereeId"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Emanuel")))
                .andExpect(jsonPath("$.surname", is("Smith")));
    }

    @Test
    public void updateRefereeAndReturn_withValidInputTest() throws Exception {
        referee1Init(referee1);
        refereeUpdate(updatedReferee);
        mvc.perform(post("/app-api/referees").contentType(MediaType.APPLICATION_JSON)
                .content(referee1.toString()));
        mvc.perform(put("/app-api/referees").contentType(MediaType.APPLICATION_JSON)
                        .content(updatedReferee.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Matty")))
                .andExpect(jsonPath("$.surname", is("Willow")))
                .andExpect(jsonPath("$.league", is("LaLiga")));
    }

}
