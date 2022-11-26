package fr.polytech.bbr.fsj.job_offer_management.repositories;

import fr.polytech.bbr.fsj.job_offer_management.models.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Collection;

@Transactional
@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    @Query(
            value = "SELECT * FROM job_offer j WHERE j.id_employer = ?1",
            nativeQuery = true)
    Collection<JobOffer> findJobOffersByIdEmployer(Long idEmployer);
    @Modifying
    @Query("UPDATE JobOffer j " +
            "SET j.title = :title, " +
            "j.description = :description, " +
            "j.beginningDate = :beginningDate, " +
            "j.endingDate = :endingDate, " +
            "j.place = :place, " +
            "j.numberPositions = :numberPositions, " +
            "j.remuneration = :remuneration, " +
            "j.publishingDate = :publishingDate " +
            "WHERE j.idJobOffer = :idJobOffer")
    void updateJobOffer(@Param("idJobOffer") Long idJobOffer,
                       @Param("title") String title,
                       @Param("description") String description,
                       @Param("beginningDate") Date beginningDate,
                       @Param("endingDate") Date endingDate,
                       @Param("place") String place,
                       @Param("numberPositions") Integer numberPositions,
                       @Param("remuneration") Double remuneration,
                       @Param("publishingDate") Date publishingDate
                       );
}

