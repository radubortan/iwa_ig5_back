package fr.polytech.bbr.fsj;

import fr.polytech.bbr.fsj.model.Candidate;
import fr.polytech.bbr.fsj.service.AppUserService;
import fr.polytech.bbr.fsj.service.CandidateService;
import fr.polytech.bbr.fsj.service.EmployerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;


@SpringBootTest
@AutoConfigureMockMvc
class UserTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppUserService appUserService;

    @MockBean
    private EmployerService employerService;

    @MockBean
    private CandidateService candidateService;

    UserTests() {
    }

    @Test
    @WithMockUser(username="bortanradu@gmail.com", password = "password", roles = "CANDIDATE")
    void shouldGetCandidate() throws Exception{
        candidateService.saveCandidate(new Candidate(1l, "White", "Walter", LocalDate.of(1990, 1, 1), "06000000"));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/users/candidate/1");

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }
}
