package fr.polytech.bbr.fsj.service;

import fr.polytech.bbr.fsj.model.Employer;
import fr.polytech.bbr.fsj.repository.EmployerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
