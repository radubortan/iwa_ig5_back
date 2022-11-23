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
    public List<Rating> getAllRatings(String id) throws NoSuchElementException {
        List<Rating> list = ratingRepo.getRatingsByIdReceiver(id);
        if (list == null) {
            throw new NoSuchElementException();
        }
        return list;
    }

    public Rating getRatingByIdSenderAndIdReceiver(String idSender, String idReceiver) throws NoSuchElementException{
        List<Rating> list = ratingRepo.getRatingsByIdReceiver(idReceiver);

        if (list.size() != 0) {
            List<Rating> filteredList = list.stream().filter(rating -> rating.getIdSender().equals(idSender)).collect(Collectors.toList());

            if (filteredList.size() != 0) {
                return filteredList.get(0);
            }
            throw new NoSuchElementException();
        }
        throw new NoSuchElementException();
    }

    //add a rating to a user by providing the receiver id in the body
    public String saveRating(Rating rating) throws IllegalArgumentException {
        if (rating.getValue() > 5 || rating.getValue() < 1) {
            throw new IllegalArgumentException();
        }
        ratingRepo.save(rating);
        return "Rating added successfully";
    }

    //remove a rating by providing the rating id
    public String deleteRating(String idRating) {
        ratingRepo.deleteById(idRating);
        return "Rating deleted successfully";
    }

    //get the sender id by providing the id of the rating
    public String getSenderId(String idRating) throws Exception{
        Rating rating = ratingRepo.findById(idRating).orElseThrow(() -> new Exception());
        return rating.getIdSender();
    }


}
