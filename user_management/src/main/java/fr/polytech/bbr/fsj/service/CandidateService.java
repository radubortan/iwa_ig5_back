package fr.polytech.bbr.fsj.service;

import fr.polytech.bbr.fsj.model.Candidate;
import fr.polytech.bbr.fsj.repository.CandidateRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CandidateService {
    private final CandidateRepo candidateRepo;

    //adds user to the database
    public void saveCandidate(Candidate candidate) {
        candidateRepo.save(candidate);
    }

    public Candidate getCandidateById(Long id) {
        return candidateRepo.findById(id).orElse(null);
    }

    public List<Candidate> getCandidates() {
        return candidateRepo.findAll();
    }

    public Candidate updateCandidate(Candidate candidate) {
        return candidateRepo.save(candidate);
    }
}
