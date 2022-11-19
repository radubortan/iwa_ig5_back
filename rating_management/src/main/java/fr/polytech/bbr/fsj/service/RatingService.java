package fr.polytech.bbr.fsj.service;

import fr.polytech.bbr.fsj.api.RatingRequest;
import fr.polytech.bbr.fsj.model.Rating;
import fr.polytech.bbr.fsj.repository.RatingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RatingService {
    private final RatingRepo ratingRepo;

    //get average rating for a user by providing their id
    public Double getAverageRating(Long id) {
        List<Rating> ratings = ratingRepo.getRatingsByIdReceiver(id);

        //if no ratings, return °
        if (ratings.size() == 0) {
            return 0d;
        }

        double sum = 0;
        for (Rating rating : ratings) {
            sum = sum + rating.getValue();
        }

        //return the sum divided by the number of ratings
        return sum / ratings.size();
    }

    //get all ratings for a user by providing the user id
    public List<Rating> getAllRatings(Long id) {
        return ratingRepo.getRatingsByIdReceiver(id);
    }

    //add a rating to a user by providing the receiver id in the body
    public String saveRating(RatingRequest request, Long idSender) {

        //check that sender and receiver have worked together

        ratingRepo.save(new Rating(request.getValue(), request.getComment(), idSender, request.getIdReceiver()));
        return "Rating added successfully";
    }

    //remove a rating by providing the rating id
    public String deleteRating(Long idRating) {
        ratingRepo.deleteById(idRating);
        return "Rating deleted successfully";
    }

    //get the sender id by providing the id of the rating
    public Long getSenderId(Long idRating) throws Exception{
        Rating rating = ratingRepo.findById(idRating).orElseThrow(() -> new Exception());
        return rating.getIdSender();
    }
}
