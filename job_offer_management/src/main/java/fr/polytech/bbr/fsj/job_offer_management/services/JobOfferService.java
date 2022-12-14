package fr.polytech.bbr.fsj.job_offer_management.services;

import fr.polytech.bbr.fsj.job_offer_management.models.JobOffer;
import fr.polytech.bbr.fsj.job_offer_management.repositories.JobOfferRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Data
@Service
public class JobOfferService {

    @Autowired
    private KafkaTemplate<String, JobOffer> kafkaTemplate;

    @Autowired
    private JobOfferRepository jobOfferRepository;

    public JobOfferService(KafkaTemplate<String, JobOffer> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }


    public Optional<JobOffer> getJobOffer(final Long id){
        return jobOfferRepository.findById(id);
    }

    public Iterable<JobOffer> getJobOffers() {
        return jobOfferRepository.findAll();
    }

    public void deleteJobOffer(final Long id) {
        jobOfferRepository.deleteById(id);
    }

    public JobOffer saveJobOffer(JobOffer jobOffer) {
        JobOffer savedJobOffer = jobOfferRepository.save(jobOffer);
        this.kafkaTemplate.send("job_offer", savedJobOffer);
        return savedJobOffer;
    }

    public Iterable<JobOffer> getJobOffersByIdEmployer(Long idEmployer){
        return jobOfferRepository.findJobOffersByIdEmployer(idEmployer);
    }

    public void updateJobOffer(Long idJobOffer, JobOffer newJobOffer){
        jobOfferRepository.updateJobOffer(
                idJobOffer,
                newJobOffer.getTitle(),
                newJobOffer.getDescription(),
                newJobOffer.getBeginningDate(),
                newJobOffer.getEndingDate(),
                newJobOffer.getPlace(),
                newJobOffer.getNumberPositions(),
                newJobOffer.getRemuneration(),
                newJobOffer.getPublishingDate()
                );
    }
}
