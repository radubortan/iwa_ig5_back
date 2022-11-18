package fr.polytech.bbr.fsj.job_offer_management.controllers;

import fr.polytech.bbr.fsj.job_offer_management.models.JobOffer;
import fr.polytech.bbr.fsj.job_offer_management.repositories.JobOfferRepository;
import fr.polytech.bbr.fsj.job_offer_management.services.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class JobOfferController {

    @Autowired
    private JobOfferService jobOfferService;

    /**
     * Read - Get job offer by id
     * @return - An Optional object of JobOffer full filled
     */
    @GetMapping("/job-offers/{id}")
    public Optional<JobOffer> getJobOffer(@RequestParam final Long id) {
        return jobOfferService.getJobOffer(id);
    }

    /**
     * Read - Get all job offers
     * @return - An Iterable object of JobOffer full filled
     */
    @GetMapping("/job-offers")
    public Iterable<JobOffer> getJobOffers() {
        return jobOfferService.getJobOffers();
    }

    /**
     * Delete - Delete job offer by id
     * @return - Void
     */
    @DeleteMapping ("/job-offers/{id}")
    public void deleteJobOffer(@RequestParam final Long id) {
        jobOfferService.deleteJobOffer(id);
    }

    /**
     * Write - Save job offer
     * @return - A saved JobOffer object
     */
    @PostMapping ("/job-offers")
    public JobOffer saveJobOffer(@RequestBody final JobOffer jobOffer) {
        System.out.println(jobOffer);
        return jobOfferService.saveJobOffer(jobOffer);
    }
}