package fr.polytech.bbr.fsj.job_offer_management.repositories;

import fr.polytech.bbr.fsj.job_offer_management.models.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    @Query(
            value = "SELECT * FROM job_offer j WHERE j.id_employer = ?1",
            nativeQuery = true)
    Collection<JobOffer> findJobOffersByIdEmployer(Long idEmployer);
}

