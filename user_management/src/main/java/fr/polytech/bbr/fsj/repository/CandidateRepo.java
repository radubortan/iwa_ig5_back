package fr.polytech.bbr.fsj.repository;

import fr.polytech.bbr.fsj.model.AppUser;
import fr.polytech.bbr.fsj.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface CandidateRepo extends JpaRepository<Candidate, String> {
    Candidate findByEmail(String email);
}
