package fr.polytech.bbr.fsj.registration;

import fr.polytech.bbr.fsj.model.AppUser;
import fr.polytech.bbr.fsj.model.Candidate;
import fr.polytech.bbr.fsj.model.Employer;
import fr.polytech.bbr.fsj.service.AppUserService;
import fr.polytech.bbr.fsj.service.CandidateService;
import fr.polytech.bbr.fsj.service.EmployerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final CandidateService candidateService;
    private final EmployerService employerService;
    private final EmailValidator emailValidator;

    //register an employer
    public String registerEmployer(RegistrationRequestEmployer request) {
        //check if email is valid
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if(!isValidEmail) {
            throw new IllegalStateException("Email not valid");
        }

        // add user to the database and get back the activation token
        appUserService.saveUser(new AppUser(
                request.getEmail(),
                request.getPassword()
        ), "ROLE_EMPLOYER");

        employerService.saveEmployer(new Employer(request.getEmail(), request.getCompanyName(), request.getAddress(), request.getPhoneNumber()));
        return "Registration successful";
    }

    //register a candidate
    public String registerCandidate(RegistrationRequestCandidate request) {
        //check if email is valid
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if(!isValidEmail) {
            throw new IllegalStateException("Email not valid");
        }

        // add user to the database and get back the activation token
        appUserService.saveUser(new AppUser(
                request.getEmail(),
                request.getPassword()
        ), "ROLE_CANDIDATE");

        candidateService.saveCandidate(new Candidate(request.getEmail(), request.getLastName(), request.getFirstName(), request.getBirthday(), request.getPhoneNumber()));
        return "Registration successful";
    }
}
