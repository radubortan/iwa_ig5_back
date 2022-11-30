package fr.polytech.bbr.fsj.service;

import fr.polytech.bbr.fsj.model.Rating;
import fr.polytech.bbr.fsj.repository.RatingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RatingService {
    private final RatingRepo ratingRepo;

    //get all ratings for a user by providing the user id

    /**
     * Get all the ratings received by a user given their id
     * @param id the id of the user
     * @return all the ratings received by the user
     */
    public List<Rating> getAllRatings(String id) {
        List<Rating> list = ratingRepo.getRatingsByIdReceiver(id);
        return list;
    }

    /**
     * Get a rating by the id of the sender and the id of the receiver
     * @param idSender the id of the sending user
     * @param idReceiver the id of the receiving user
     * @return a rating
     * @throws NoSuchElementException
     */
    public Rating getRatingByIdSenderAndIdReceiver(String idSender, String idReceiver) throws NoSuchElementException {
        List<Rating> list = ratingRepo.getRatingsByIdReceiver(idReceiver);

        if (list.size() != 0) {
            List<Rating> filteredList = list.stream().filter(rating -> rating.getIdSender().equals(idSender)).collect(Collectors.toList());

            if (filteredList.size() != 0) {
                return filteredList.get(0);
            }
            return null;
        }
        return null;
    }

    /**
     * Adds a rating to the database
     * @param rating the rating to add
     * @return a confirmation message
     * @throws IllegalArgumentException
     */
    public String saveRating(Rating rating) throws IllegalArgumentException {
        if (rating.getValue() > 5 || rating.getValue() < 1) {
            throw new IllegalArgumentException();
        }
        ratingRepo.save(rating);
        return "Rating added successfully";
    }

    /**
     * Removes a rating given its id
     * @param idRating the id of the rating to remove
     * @return a confirmation message
     */
    public String deleteRating(String idRating) {
        ratingRepo.deleteById(idRating);
        return "Rating deleted successfully";
    }

    /**
     * Get the id of the sender of a rating given the rating id
     * @param idRating the id of the rating
     * @return the id of the sender
     * @throws Exception
     */
    public String getSenderId(String idRating) throws Exception{
        Rating rating = ratingRepo.findById(idRating).orElseThrow(() -> new Exception());
        return rating.getIdSender();
    }


}
