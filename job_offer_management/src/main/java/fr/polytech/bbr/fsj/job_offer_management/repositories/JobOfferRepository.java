package fr.polytech.bbr.fsj.job_offer_management.repositories;

import fr.polytech.bbr.fsj.job_offer_management.models.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {}

