package fr.polytech.bbr.fsj;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.polytech.bbr.fsj.api.RatingRequest;
import fr.polytech.bbr.fsj.model.Rating;
import fr.polytech.bbr.fsj.service.RatingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class RatingManagementControllerTests {
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @Test
    @DisplayName("Should return all ratings given user id")
    public void shouldReturnAllRatings() throws Exception {
        Rating rating1 = new Rating("1", 5, "comment1", "1", "2");
        Rating rating2 = new Rating("2", 5, "comment2", "3", "2");
        List<Rating> list = Arrays.asList(rating1, rating2);
        doReturn(list).when(ratingService).getAllRatings("2");

        mockMvc.perform(get("/api/ratings/{id}", 1000))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Should return a rating given a sender id and receiver id")
    public void shouldReturnRatingGivenIdSenderAndIdReceiver() throws Exception {
        Rating rating = new Rating("1", 5, "comment1", "1", "2");
        doReturn(rating).when(ratingService).getRatingByIdSenderAndIdReceiver("1", "2");

        mockMvc.perform(get("/api/ratings/{idSender}/{idReceiver}", "1", "2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @DisplayName("Should return not found given a bad sender id and receiver id")
    public void shouldReturnRatingNotFoundGivenIdSenderAndIdReceiver() throws Exception {
        doReturn(null).when(ratingService).getRatingByIdSenderAndIdReceiver("1", "2");

        mockMvc.perform(get("/api/ratings/{idSender}/{idReceiver}", "1", "3"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should add a rating")
    public void shouldAddRating() throws Exception {
        //generate token for authentication
        Algorithm algorithm = Algorithm.HMAC256("fsj-Secret".getBytes());
        String accessToken = JWT.create()
                .withSubject("")
                .withExpiresAt(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                .withIssuer("")
                .withClaim("roles", "ROLE_EMPLOYER")
                .withClaim("accountId", "1")
                .sign(algorithm);

        RatingRequest ratingRequest = new RatingRequest("1", 5, "comment", "4");
        Rating rating = new Rating("1", 5, "comment", "1", "4");
        doReturn("Rating added successfully").when(ratingService).saveRating(rating);

        mockMvc.perform(post("/api/ratings/new")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "+accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ratingRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Rating added successfully"));
    }


    //doesn't work, always returns 200
    @Test
    @DisplayName("Should get bad request while adding a rating")
    public void shouldGetBadRequestAddRating() throws Exception {
        //generate token for authentication
        Algorithm algorithm = Algorithm.HMAC256("fsj-Secret".getBytes());
        String accessToken = JWT.create()
                .withSubject("")
                .withExpiresAt(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                .withIssuer("")
                .withClaim("roles", "ROLE_EMPLOYER")
                .withClaim("accountId", "1")
                .sign(algorithm);

        RatingRequest ratingRequest = new RatingRequest("1", 10, "comment", "4");
        Rating rating = new Rating("1", 5, "comment", "1", "4");
        doReturn("Bad request").when(ratingService).saveRating(rating);

        mockMvc.perform(post("/api/ratings/new")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "+accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ratingRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Bad request"));
    }
}
