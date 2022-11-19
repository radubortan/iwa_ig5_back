package fr.polytech.bbr.fsj.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import fr.polytech.bbr.fsj.model.Rating;
import fr.polytech.bbr.fsj.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RatingController {
    private final RatingService ratingService;

    //get average rating for a user by providing their id
    @GetMapping("/ratings/average/{id}")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long id) {
        return ResponseEntity.ok().body(ratingService.getAverageRating(id));
    }

    //get all ratings for a user by providing the user id
    @GetMapping("/ratings/{id}")
    public ResponseEntity<List<Rating>> getAllRatings(@PathVariable Long id) {
        return ResponseEntity.ok().body(ratingService.getAllRatings(id));
    }

    //add a rating to a user by providing the receiver id in the body
   @PostMapping("/ratings/new")
    public ResponseEntity<String> postRating(@RequestBody RatingRequest request, @RequestHeader(AUTHORIZATION) String jwt) {
       try {
           String token = jwt.substring("Bearer ".length());
           Algorithm algorithm = Algorithm.HMAC256("fsj-Secret".getBytes());
           JWTVerifier verifier = JWT.require(algorithm).build();
           DecodedJWT decodedJWT = verifier.verify(token);
           Long idSender = decodedJWT.getClaim("accountId").asLong();

           return ResponseEntity.ok().body(ratingService.saveRating(request, idSender));
       }
       catch (Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
       }
    }

    //remove a rating by providing the rating id
    @DeleteMapping("/ratings/delete/{idRating}")
    public ResponseEntity<String> deleteRating(@PathVariable Long idRating, @RequestHeader(AUTHORIZATION) String jwt) {
        try {
            String token = jwt.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256("fsj-Secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            Long idSenderToken = decodedJWT.getClaim("accountId").asLong();

            Long idSender = ratingService.getSenderId(idRating);

            //making sure that the id in the token is the same as the sender id in the database
            if (!idSender.equals(idSenderToken)) {
                System.out.println(idSender);
                System.out.println(idSenderToken);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorised");
            }
            return ResponseEntity.ok().body(ratingService.deleteRating(idRating));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
        }
    }
}
