package fr.polytech.bbr.fsj.registration;

import fr.polytech.bbr.fsj.model.AppUser;
import fr.polytech.bbr.fsj.model.Candidate;
import fr.polytech.bbr.fsj.model.Employer;
import fr.polytech.bbr.fsj.service.AppUserService;
import fr.polytech.bbr.fsj.service.CandidateService;
import fr.polytech.bbr.fsj.service.EmployerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final CandidateService candidateService;
    private final EmployerService employerService;
    private final EmailValidator emailValidator;

    /**
     * Registers an employer
     * @param request information about the employer
     * @return a string with a message
     * @throws IllegalArgumentException
     */
    public String registerEmployer(RegistrationRequestEmployer request) throws IllegalArgumentException {
        //check if email is valid
        boolean isValidEmail = emailValidator.test(request.getEmail());

        boolean paramsProvided = request.getEmail() != null
                && request.getPassword() != null
                && request.getCompanyName() != null
                && request.getAddress() != null
                && request.getPhoneNumber() != null;

        if(!isValidEmail || !paramsProvided) {
            throw new IllegalArgumentException("Bad information provided");
        }

        String id = UUID.randomUUID().toString();

        // add user to the database
        appUserService.saveUser(new AppUser(
                id,
                request.getEmail(),
                request.getPassword()
        ), "ROLE_EMPLOYER");

        employerService.saveEmployer(new Employer(id, request.getCompanyName(), request.getAddress(), request.getPhoneNumber()));
        return "Registration successful";
    }

    /**
     * Registers a candidate
     * @param request information about the candidate
     * @return a string with a message
     * @throws IllegalArgumentException
     */
    public String registerCandidate(RegistrationRequestCandidate request) throws IllegalArgumentException {
        //check if email is valid
        boolean isValidEmail = emailValidator.test(request.getEmail());

        boolean paramsProvided = request.getEmail() != null
                && request.getPassword() != null
                && request.getLastName() != null
                && request.getFirstName() != null
                && request.getBirthday() != null
                && request.getPhoneNumber() != null;

        if(!isValidEmail || !paramsProvided) {
            throw new IllegalArgumentException("Bad information provided");
        }

        String id = UUID.randomUUID().toString();

        // add user to the database
        appUserService.saveUser(new AppUser(
                id,
                request.getEmail(),
                request.getPassword()
        ), "ROLE_CANDIDATE");

        candidateService.saveCandidate(new Candidate(id, request.getLastName(), request.getFirstName(), request.getBirthday(), request.getPhoneNumber()));
        return "Registration successful";
    }
}
