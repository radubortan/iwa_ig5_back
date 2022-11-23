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

    //adds user to the database
    public void saveCandidate(Candidate candidate) {
        candidateRepo.save(candidate);
    }

    public Candidate getCandidateById(String id) {
        return candidateRepo.findById(id).orElse(null);
    }

    public List<Candidate> getCandidates() {
        return candidateRepo.findAll();
    }

    public Candidate updateCandidate(Candidate candidate) {
        return candidateRepo.save(candidate);
    }

    public String getCvLink(String idCandidate) throws NoSuchElementException{
        Candidate candidate = candidateRepo.findById(idCandidate).orElse(null);
        if (candidate == null) {
            throw new NoSuchElementException();
        }

        return candidate.getLinkCv();
    }

    public String getCvKeyboards(String idCandidate) throws NoSuchElementException{
        Candidate candidate = candidateRepo.findById(idCandidate).orElse(null);
        if (candidate == null) {
            throw new NoSuchElementException();
        }

        return candidate.getCvKeywords();
    }

    public String updateCvLink(String idCandidate, String cvLink) {
        Candidate candidate = candidateRepo.findById(idCandidate).orElse(null);
        if (candidate == null) {
            throw new NoSuchElementException();
        }
        candidate.setLinkCv(cvLink);
        candidateRepo.save(candidate);
        return "Updated CV link";
    }

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
