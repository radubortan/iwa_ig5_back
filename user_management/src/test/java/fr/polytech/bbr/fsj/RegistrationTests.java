package fr.polytech.bbr.fsj;

import fr.polytech.bbr.fsj.registration.RegistrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
class RegistrationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;

    @Test
    void shouldSignUpEmployerTest() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.post("/api/registration/employer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"email@gmail.com\", \"password\": \"password\", \"phoneNumber\": \"06\", \"lastName\": \"Johnson\", \"firstName\": \"Alex\", \"birthday\": \"2000-01-01\" }");

        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void shouldSignUpCandidateTest() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.post("/api/registration/candidate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"email@gmail.com\", \"password\": \"password\", \"phoneNumber\": \"06\", \"companyName\": \"Polytech\", \"address\": \"Montpellier\" }");

        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void emailWrongFormatTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/api/registration/candidate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"@@\", \"password\": \"password\" }");
        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

/*    @Test
    void emailAlreadyTakenTest() throws Exception {
        RequestBuilder requestCandidate = MockMvcRequestBuilders.post("/api/registration/candidate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"email@gmail.com\", \"password\": \"password\" }");
        mockMvc.perform(requestCandidate).andExpect(MockMvcResultMatchers.status().isConflict());

        RequestBuilder requestEmployer = MockMvcRequestBuilders.post("/api/registration/employer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"email@gmail.com\", \"password\": \"password\" }");
        mockMvc.perform(requestEmployer).andExpect(MockMvcResultMatchers.status().isConflict());
    }*/

}
