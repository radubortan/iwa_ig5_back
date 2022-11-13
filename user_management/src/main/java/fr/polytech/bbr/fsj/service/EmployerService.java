package fr.polytech.bbr.fsj.service;

import fr.polytech.bbr.fsj.model.Employer;
import fr.polytech.bbr.fsj.repository.EmployerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployerService {
    private final EmployerRepo employerRepo;

    //adds user to the database and generates the activation token
    public void saveEmployer(Employer employer) {
        employerRepo.save(employer);
    }

    public Optional<Employer> getEmployer(Long id) {
        return employerRepo.findById(id);
    }

    public List<Employer> getCandidates() {
        return employerRepo.findAll();
    }
}
