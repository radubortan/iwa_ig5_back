package fr.polytech.bbr.fsj.service;

import fr.polytech.bbr.fsj.model.AppUser;
import fr.polytech.bbr.fsj.model.Candidate;
import fr.polytech.bbr.fsj.model.Employer;
import fr.polytech.bbr.fsj.model.Role;
import fr.polytech.bbr.fsj.registration.token.ConfirmationToken;
import fr.polytech.bbr.fsj.registration.token.ConfirmationTokenService;
import fr.polytech.bbr.fsj.repository.AppUserRepo;
import fr.polytech.bbr.fsj.repository.CandidateRepo;
import fr.polytech.bbr.fsj.repository.EmployerRepo;
import fr.polytech.bbr.fsj.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployerService {
    private final EmployerRepo employerRepo;

    //adds user to the database and generates the activation token
    public void saveEmployer(Employer employer) {
        employerRepo.save(employer);
    }

    public Employer getEmployer(String email) {
        return employerRepo.findByEmail(email);
    }

    public List<Employer> getCandidates() {
        return employerRepo.findAll();
    }
}
