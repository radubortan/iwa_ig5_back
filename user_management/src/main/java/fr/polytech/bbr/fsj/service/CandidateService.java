package fr.polytech.bbr.fsj.service;

import fr.polytech.bbr.fsj.model.Candidate;
import fr.polytech.bbr.fsj.repository.CandidateRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CandidateService {
    private final CandidateRepo candidateRepo;

    //adds user to the database and generates the activation token
    public void saveCandidate(Candidate candidate) {
        candidateRepo.save(candidate);
    }

    public Optional<Candidate> getCandidate(Long id) {
        return candidateRepo.findById(id);
    }

    public List<Candidate> getCandidates() {
        return candidateRepo.findAll();
    }
}
