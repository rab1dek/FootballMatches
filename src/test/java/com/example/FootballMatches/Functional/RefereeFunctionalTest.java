package com.example.FootballMatches.Functional;

import com.example.FootballMatches.FootballMatchesApplication;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

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
        referee.put("name", "Jacobo");
        referee.put("doctorID", 1);
        referee.put("surname", "Coco");
        referee.put("email", "coco@gmail.com");
        referee.put("phone", "602657001");
        referee.put("speciality", "Doctor");
    }

}
