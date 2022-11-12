package fr.polytech.bbr.fsj.repository;

import fr.polytech.bbr.fsj.model.Candidate;
import fr.polytech.bbr.fsj.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface EmployerRepo extends JpaRepository<Employer, String> {
    Employer findByEmail(String email);
}
