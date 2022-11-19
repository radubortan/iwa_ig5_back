package fr.polytech.bbr.fsj.repository;

import fr.polytech.bbr.fsj.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RatingRepo extends JpaRepository<Rating, Long> {
    List<Rating> getRatingsByIdReceiver(Long id);
}
