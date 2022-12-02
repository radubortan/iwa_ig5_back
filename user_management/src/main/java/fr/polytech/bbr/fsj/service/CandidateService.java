package fr.polytech.bbr.fsj.service;

import fr.polytech.bbr.fsj.model.Candidate;
import fr.polytech.bbr.fsj.repository.CandidateRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class CandidateService {
    private final CandidateRepo candidateRepo;

    /**
     * Adds a candidate to the database
     * @param candidate the candidate
     */
    public void saveCandidate(Candidate candidate) {
        candidateRepo.save(candidate);
    }

    /**
     * Gets a candidate by their id
     * @param id the id of the candidate
     * @return the candidate
     */
    public Candidate getCandidateById(String id) {
        return candidateRepo.findById(id).orElse(null);
    }

    /**
     * Get all candidates
     * @return a list of all the candidates
     */
    public List<Candidate> getCandidates() {
        return candidateRepo.findAll();
    }

    /**
     * Update the information of a candidate
     * @param candidate a candidate object with the new information
     * @return the updated candidate
     */
    public Candidate updateCandidate(Candidate candidate) {
        return candidateRepo.save(candidate);
    }

    /**
     * Get the CV link of a candidate given their id
     * @param idCandidate the id of the candidate
     * @return the CV link
     * @throws NoSuchElementException
     */
    public String getCvLink(String idCandidate) throws NoSuchElementException{
        Candidate candidate = candidateRepo.findById(idCandidate).orElse(null);
        if (candidate == null) {
            throw new NoSuchElementException();
        }

        return candidate.getLinkCv();
    }

    /**
     * Get the CV keywords of a candidate given their id
     * @param idCandidate the id of the candidate
     * @return the CV keywords
     * @throws NoSuchElementException
     */
    public String getCvKeyboards(String idCandidate) throws NoSuchElementException{
        Candidate candidate = candidateRepo.findById(idCandidate).orElse(null);
        if (candidate == null) {
            throw new NoSuchElementException();
        }

        return candidate.getCvKeywords();
    }

    /**
     * Updates the CV link of a candidate
     * @param idCandidate the id of the candidate
     * @param cvLink the new CV link
     * @return the new CV link
     */
    public String updateCvLink(String idCandidate, String cvLink) {
        Candidate candidate = candidateRepo.findById(idCandidate).orElse(null);
        if (candidate == null) {
            throw new NoSuchElementException();
        }
        candidate.setLinkCv(cvLink);
        candidateRepo.save(candidate);
        return "Updated CV link";
    }

    /**
     * Update the CV keywords of a candidate
     * @param idCandidate the id of the candidate
     * @param cvKeywords the new CV keywords
     * @return the new CV keywords
     */
    public String updateCvKeywords(String idCandidate, String cvKeywords) {
        Candidate candidate = candidateRepo.findById(idCandidate).orElse(null);
        if (candidate == null) {
            throw new NoSuchElementException();
        }
        candidate.setCvKeywords(cvKeywords);
        candidateRepo.save(candidate);
        return "Updated CV keywords";
    }
}
