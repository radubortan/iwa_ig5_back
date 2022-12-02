package fr.polytech.bbr.fsj.api;

import fr.polytech.bbr.fsj.model.Rating;
import fr.polytech.bbr.fsj.security.JWTDecryption;
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

    /**
     * Get all the rating received by a user given their id
     * @param id the id of the user
     * @return a list of all the ratings received by the user
     */
    @GetMapping("/ratings/{id}")
    public ResponseEntity<List<Rating>> getAllRatings(@PathVariable String id) {
        return ResponseEntity.ok().body(ratingService.getAllRatings(id));
    }

    /**
     * Get a rating by the id of the sender and the id of the receiver
     * @param idSender the id of the sending user
     * @param idReceiver the id of the receiving user
     * @return a rating
     */
    @GetMapping("/ratings/{idSender}/{idReceiver}")
    public ResponseEntity<Rating> getRatingByIdSenderAndIdReceiver(@PathVariable String idSender, @PathVariable String idReceiver) {
        Rating rating = ratingService.getRatingByIdSenderAndIdReceiver(idSender, idReceiver);
        if (rating == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok().body(rating);
    }

    /**
     * Adds a rating to the database
     * @param request information about the rating
     * @param jwt token for authorisation
     * @return a confirmation message
     */
   @PostMapping("/ratings/new")
    public ResponseEntity<String> postRating(@RequestBody RatingRequest request, @RequestHeader(AUTHORIZATION) String jwt) {
       System.out.println("Post");
       try {
           JWTDecryption jwtDecryption = new JWTDecryption(jwt);
           String idSender = jwtDecryption.getAccountId();

           //prevent user from rating themselves
           if (!idSender.equals(request.getIdReceiver())) {
               return ResponseEntity.ok().body(ratingService.saveRating(new Rating(request.getId(),request.getValue(), request.getComment(), idSender, request.getIdReceiver())));
           }
           return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not allowed to rate yourself");
       }
       catch (IllegalStateException e) {
           return ResponseEntity.status(HttpStatus.CONFLICT).body("A rating already exists");
       }
       catch (Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
       }
    }

    /**
     * Removes a rating given its id
     * @param idRating id of the rating to remove
     * @param jwt token for authorisation
     * @return a confirmation message
     */
    @DeleteMapping("/ratings/delete/{idRating}")
    public ResponseEntity<String> deleteRating(@PathVariable String idRating, @RequestHeader(AUTHORIZATION) String jwt) {
        try {
            JWTDecryption jwtDecryption = new JWTDecryption(jwt);
            String idSenderToken = jwtDecryption.getAccountId();

            String idSender = ratingService.getSenderId(idRating);

            //making sure that the id in the token is the same as the sender id in the database
            if (!idSender.equals(idSenderToken)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorised");
            }
            return ResponseEntity.ok().body(ratingService.deleteRating(idRating));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
        }
    }
}
