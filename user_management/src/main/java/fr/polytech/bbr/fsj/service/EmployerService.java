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

    /**
     * Adds an employer to the database
     * @param employer the employer
     */
    public void saveEmployer(Employer employer) {
        employerRepo.save(employer);
    }

    /**
     * Gets an employer by their id
     * @param id the id of the employer
     * @return the employer
     */
    public Employer getEmployerById(String id) {
        return employerRepo.findById(id).orElse(null);
    }

    /**
     * Get all employers
     * @return all employers
     */
    public List<Employer> getEmployers() {
        return employerRepo.findAll();
    }

    /**
     * Update the information of an employer
     * @param employer an employer object with the new information
     * @return the updated employer
     */
    public Employer updateEmployer(Employer employer) {
        return employerRepo.save(employer);
    }
}
