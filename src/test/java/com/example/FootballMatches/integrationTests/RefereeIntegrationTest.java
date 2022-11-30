package com.example.FootballMatches.integrationTests;

import com.example.FootballMatches.FootballMatchesApplication;
import com.example.FootballMatches.entities.Referee;
import com.example.FootballMatches.repositories.RefereeRepository;
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

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
public class RefereeIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private RefereeRepository refereeRepository;

    ObjectMapper objectMapper = new ObjectMapper();
    public Referee createRef() {
        Referee referee = new Referee();
        referee.setRefereeId(1L);
        referee.setName("Szymon");
        referee.setSurname("Marciniak");
        referee.setLeague("Ekstraklasa");
        return referee;
    }
    @Test
    public void createRefereeAndFindId() throws Exception {
        Referee referee = createRef();
        mvc.perform(post("/app-api/referees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(referee)));
        Optional<Referee> foundRef = refereeRepository.findById(1L);
        assertEquals(foundRef.get().getName(), "Szymon");
    }
    @Test
    public void updateReferee() throws Exception {
        Referee referee = createRef();
        mvc.perform(post("/app-api/referees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(referee)));
        referee.setName("Tomasz");
        referee.setSurname("Musial");
        referee.setLeague("Champions League");
        mvc.perform(put("/app-api/referees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(referee)));
        Optional<Referee> foundRef = refereeRepository.findById(1L);
        assertEquals(foundRef.get().getName(), "Tomasz");
    }
    @Test
    public void getReferees() throws Exception {
        Referee referee1 = new Referee();
        Referee referee = createRef();
        mvc.perform(post("/app-api/referees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(referee)));
        referee1.setRefereeId(2L);
        referee1.setName("Tomasz");
        referee1.setSurname("Musial");
        referee1.setLeague("Ekstraklasa");
        mvc.perform(post("/app-api/referees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(referee1)));
        mvc.perform(get("/app-api/referees").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].name", is("Szymon")))
                .andExpect(jsonPath("$[1].name", is("Tomasz")));
    }
    @Test
    public void getRefereeById() throws Exception {
        Referee referee = createRef();
        mvc.perform(post("/app-api/referees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(referee)));
        mvc.perform(get("/app-api/referees/{id}", referee.getRefereeId()).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is("Szymon")));
    }
    @Test
    public void deleteReferee() throws Exception {
        Referee referee = createRef();
        mvc.perform(post("/app-api/referees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(referee)));
        Optional<Referee> foundRef = refereeRepository.findById(1L);
        mvc.perform(delete("/app-api/referees/{id}", foundRef.get().getRefereeId()));
        Optional<Referee> foundRef1 = refereeRepository.findById(1L);
        assertTrue(foundRef1.isEmpty());
    }
}
